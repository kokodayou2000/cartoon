package org.example.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.component.PayFactory;
import org.example.config.RabbitMQConfig;
import org.example.constant.TimeConstant;
import org.example.core.AjaxResult;
import org.example.enums.*;
import org.example.feign.ProductFeignService;
import org.example.feign.UserFeignService;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.mapper.ChargeMapper;
import org.example.mapper.ProductOrderItemMapper;
import org.example.mapper.ProductOrderMapper;
import org.example.model.*;
import org.example.request.ChargeReq;
import org.example.request.ChargeRequest;
import org.example.request.ConfirmOrderRequest;
import org.example.request.UserChargeReq;
import org.example.service.ProductOrderService;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.example.vo.CartOrderItemVO;
import org.example.vo.PayInfoVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kokodayou
 * @since 2023-07-16
 */
@Service
@Slf4j
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private ProductOrderItemMapper productOrderItemMapper;

    @Autowired
    private ChargeMapper chargeMapper;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private PayFactory payFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserFeignService userFeignService;

    public AjaxResult handlerOrderCallbackMsg(ProductOrderPayTypeEnum payTypeEnum, Map<String, String> paramsToMap) {
        // 获取订单号
        String outTradeNo = paramsToMap.get("out_trade_no");
        // 交易的状态
        String tradeStatus = paramsToMap.get("trade_status");

        if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)  || "TRADE_FINISHED".equalsIgnoreCase(tradeStatus)){
            LambdaQueryWrapper<ProductOrderDO> queryWrapper = new QueryWrapper<ProductOrderDO>().lambda().eq(ProductOrderDO::getOutTradeNo, outTradeNo);
            ProductOrderDO orderDO = productOrderMapper.selectOne(queryWrapper);
            if (orderDO == null) {
                return AjaxResult.error("查询订单失败");
            }
            UserChargeReq userChargeReq = new UserChargeReq();
            userChargeReq.setUserId(orderDO.getUserId());
            userChargeReq.setPoint(orderDO.getPoint());
            // 充值
            AjaxResult ajaxResult = userFeignService.charge(userChargeReq);
            if (!Objects.equals(String.valueOf(ajaxResult.get("code")), "200")){
                return AjaxResult.error("充值失败");
            }
            // 只有是new的状态才更新成pay状态
            productOrderMapper.updateOrderPayState(outTradeNo, ProductOrderStateEnum.PAY.name(),ProductOrderStateEnum.NEW.name());
            // 更新订单状态
            return AjaxResult.success();
        }else {
            productOrderMapper.updateOrderPayState(outTradeNo, ProductOrderStateEnum.PAY.name(),ProductOrderStateEnum.CANCEL.name());
            return AjaxResult.error("支付取消了");
        }
    }

    // 确认订单
    @Override
    public AjaxResult confirmOrder(ConfirmOrderRequest req) {
        return null;
    }

    // 充值接口
    @Override
    public AjaxResult charge(ChargeReq req) {

        // 生成订单号
        String orderOutTradeNO = CommonUtil.getRandomCode(32);
        // 要充值的item
        ChargeDO chargeDO = chargeMapper.selectById(req.getChargeId());
        BaseUser baseUser = TokenCheckInterceptor.tl.get();

        AjaxResult balance = userFeignService.balance(baseUser.getId());
        if (!Objects.equals(String.valueOf(balance.get("code")), "200")){
            return AjaxResult.error("获取用户当前点数失败");
        }
        String data = balance.get("data").toString();
        int beforePoint = Integer.parseInt(data);

        // 创建订单
        ProductOrderDO orderDO = saveProductOrder(baseUser, orderOutTradeNO);
        orderDO.setOrderType(ProductOrderTypeEnum.CHARGE.name());
        orderDO.setPayAmount(BigDecimal.valueOf(chargeDO.getMoney()));
        orderDO.setTotalAmount(BigDecimal.valueOf(chargeDO.getMoney()));
        orderDO.setPayType(ProductOrderPayTypeEnum.ALIPAY.name());
        // 记录本次交易的点数转换记录
        orderDO.setBeforePoint(beforePoint);
        orderDO.setPoint(chargeDO.getPoint());

        // 创建产品消费记录
        productOrderMapper.insert(orderDO);

        // 发送延迟消息，用于自动关单
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOutTradeNo(orderOutTradeNO);

        // send
        rabbitTemplate.convertAndSend(rabbitMQConfig.getEventExchange(),rabbitMQConfig.getOrderCloseDelayRoutingKey(),orderMessage);

        // 创建支付信息 订单号，支付金额 支付类型，标题，描述，超时时间
        PayInfoVO payInfoVO = new PayInfoVO();
        payInfoVO.setClientType(req.getClientType());
        payInfoVO.setOutTradeNo(orderOutTradeNO);
        payInfoVO.setPayType(orderDO.getPayType());
        payInfoVO.setTitle("充值");
        payInfoVO.setDescription("");
        payInfoVO.setPayFee(orderDO.getPayAmount());
        payInfoVO.setOrderPayTimeoutMills(TimeConstant.ORDER_PAY_TIMEOUT_MILLS);
        String payResult = payFactory.pay(payInfoVO);
        if (StringUtils.isNotBlank(payResult)){
            log.info("创建成功");
            return AjaxResult.success(payResult);
        }else{
            // 创建订单失败
            log.error("创建订单失败:payInfo={},payResult={}",payInfoVO,payResult);
            return AjaxResult.error("error");
        }
    }

    @Override
    public boolean closeProductOrder(OrderMessage orderMessage) {
        // 查询订单对象
        ProductOrderDO productOrderDO = productOrderMapper.selectOne(new QueryWrapper<ProductOrderDO>().lambda()
                .eq(ProductOrderDO::getOutTradeNo, orderMessage.getOutTradeNo()));

        if (productOrderDO == null){
            log.warn("订单不存在 {}",orderMessage);
            return true;
        }

        // 如果已经支付了，幂等
        if (productOrderDO.getState().equalsIgnoreCase(ProductOrderStateEnum.PAY.name())){
            log.info("订单已经支付 {}",orderMessage );
            return true;
        }

        PayInfoVO payInfoVO = new PayInfoVO();
        payInfoVO.setPayType(productOrderDO.getPayType());
        payInfoVO.setOutTradeNo(productOrderDO.getOutTradeNo());
        String payResult = payFactory.queryPaySuccess(payInfoVO);

        // 如果结果为空，则未支付，取消订单
        if (org.apache.commons.lang.StringUtils.isBlank(payResult)){
            // 只有是NEW状态的时候，才会变成取消状态
            if (productOrderDO.getState().equalsIgnoreCase(ProductOrderStateEnum.NEW.name())){
                productOrderDO.setState(ProductOrderStateEnum.CANCEL.name());
                productOrderMapper.updateById(productOrderDO);
                log.info("取消订单成功 {}",orderMessage);
                return true;
            }
        }else{
            // 说明已经支付了
            log.warn("支付成功，可能第三方支付回调有问题 {}",orderMessage);
            // 更新订单状态为已支付
            if (productOrderDO.getState().equalsIgnoreCase(ProductOrderStateEnum.NEW.name())){
                // TODO 查看当前的point是否与用户的point相同?
                // 根据支付类型确定点数的使用方法
                UserChargeReq userChargeReq = new UserChargeReq();
                userChargeReq.setUserId(productOrderDO.getUserId());
                userChargeReq.setPoint(productOrderDO.getPoint());
                if (Objects.equals(productOrderDO.getOrderType(), PayStateEnum.CREATE.name())){
                    userFeignService.charge(userChargeReq);
                }else if (Objects.equals(productOrderDO.getOrderType(), PayStateEnum.SHOPPING.name())) {
                    // 假如是消费的话,将本次消费对应的 orderItem 设置一下状态
                    userFeignService.pay(userChargeReq);
                }
                productOrderDO.setState(ProductOrderStateEnum.PAY.name());
                productOrderMapper.updateById(productOrderDO);
                log.info("订单支付成功 {}",orderMessage);
                return true;
            }
        }

        return false;
    }


    private ProductOrderDO saveProductOrder( BaseUser loginUser, String orderOutTradeNO) {
        ProductOrderDO productOrderDO = new ProductOrderDO();
        productOrderDO.setUserId(loginUser.getId());
        productOrderDO.setNickname(loginUser.getName());
        productOrderDO.setOutTradeNo(orderOutTradeNO);
        productOrderDO.setCreateTime(new Date());
        productOrderDO.setDel(0);
        productOrderDO.setUpdateTime(new Date());
        // 充值
        productOrderDO.setOrderType(ProductOrderTypeEnum.CHARGE.name());
        // 设置订单状态为 new
        productOrderDO.setState(ProductOrderStateEnum.NEW.name());
        // 地址
        // 会自动插入 productOrderDO 的 id

        return productOrderDO;
    }

    // 根据购物数量，创建订单项
    private void saveProductOrderItems(String orderOutTradeNO, Long orderId, CartOrderItemVO orderItem) {
        List<String> chapterList = orderItem.getChapterList();
        String cartoonId = orderItem.getCartoonId();
        AjaxResult result = productFeignService.price(cartoonId);
        if (String.valueOf(result.get("code")) != "200"){
            return;
        }
        // 单品价格
        Integer price = Integer.parseInt((String) result.get("data"));
        List<ProductOrderItemDO> productOrderItemDOS = chapterList.stream().map((chapterId) -> {
            ProductOrderItemDO itemDO = new ProductOrderItemDO();
            itemDO.setOutTradeNo(orderOutTradeNO);
            itemDO.setProductId(chapterId);
            itemDO.setCreateTime(new Date());
            itemDO.setProductOrderId(orderId);
            itemDO.setAmount(price);
            return itemDO;
        }).collect(Collectors.toList());
        productOrderItemMapper.insertBatch(productOrderItemDOS);
    }

    private ProductOrderDO saveProductOrder(ConfirmOrderRequest request, BaseUser loginUser, String orderOutTradeNO) {
        ProductOrderDO productOrderDO = new ProductOrderDO();
        productOrderDO.setUserId(loginUser.getId());
        productOrderDO.setNickname(loginUser.getName());
        productOrderDO.setOutTradeNo(orderOutTradeNO);
        productOrderDO.setCreateTime(new Date());
        productOrderDO.setDel(0);
        // 普通订单
        productOrderDO.setOrderType(ProductOrderTypeEnum.DAILY.name());
        // 实际支付的价格
        productOrderDO.setPayAmount(BigDecimal.valueOf(request.getTotalAmount()));
        // 未使用优惠卷的价格
        productOrderDO.setTotalAmount(BigDecimal.valueOf(request.getTotalAmount()));
        // 设置订单状态为 new
        productOrderDO.setState(ProductOrderStateEnum.NEW.name());
        // 支付方式
        productOrderDO.setPayType(request.getPayType());
        productOrderMapper.insert(productOrderDO);
        return productOrderDO;
    }
}
