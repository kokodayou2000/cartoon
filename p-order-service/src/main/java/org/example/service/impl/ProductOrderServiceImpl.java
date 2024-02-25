package org.example.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.component.PayFactory;
import org.example.core.AjaxResult;
import org.example.enums.BizCodeEnum;
import org.example.enums.ProductOrderPayTypeEnum;
import org.example.enums.ProductOrderStateEnum;
import org.example.enums.ProductOrderTypeEnum;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.mapper.ProductOrderMapper;
import org.example.model.BaseUser;
import org.example.model.ProductOrderDO;
import org.example.model.ProductOrderItemDO;
import org.example.request.ConfirmOrderRequest;
import org.example.request.UserPointReq;
import org.example.service.ProductOrderService;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.example.vo.PayInfoVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

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
    private PayFactory payFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public AjaxResult handlerOrderCallbackMsg(ProductOrderPayTypeEnum payTypeEnum, Map<String, String> paramsToMap) {
        if (payTypeEnum.name().equalsIgnoreCase(ProductOrderPayTypeEnum.ALIPAY.name())){
            // 获取订单号
            String outTradeNo = paramsToMap.get("out_trade_no");
            // 交易的状态
            String tradeStatus = paramsToMap.get("trade_status");
            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)  || "TRADE_FINISHED".equalsIgnoreCase(tradeStatus)){
                // 只有是new的状态才更新成pay状态
                productOrderMapper.updateOrderPayState(outTradeNo, ProductOrderStateEnum.PAY.name(),ProductOrderStateEnum.NEW.name());
                // 更新订单状态
                return AjaxResult.success();
            }
        } else if (payTypeEnum.name().equalsIgnoreCase(ProductOrderPayTypeEnum.WECHAT.name())){

        }
        return AjaxResult.error("支付失败");
    }

    // 确认订单
    @Override
    public AjaxResult confirmOrder(ConfirmOrderRequest req) {
        return null;
    }

    // 充值接口
    @Override
    public AjaxResult charge(UserPointReq req) {
        // 创建订单
        ProductOrderDO orderDO = new ProductOrderDO();
        // 生成订单号
        String orderOutTradeNO = CommonUtil.getRandomCode(32);


        ConfirmOrderRequest orderRequest = new ConfirmOrderRequest();
        orderRequest.setShopId("0");
        orderRequest.setPayType(ProductOrderPayTypeEnum.ALIPAY.name());
        orderRequest.setTotalAmount(BigDecimal.valueOf(req.getPoint()));
        orderRequest.setChapterIdList(new HashSet<>());
        BaseUser baseUser = TokenCheckInterceptor.tl.get();

        saveProductOrder(orderRequest,baseUser,orderOutTradeNO);
        // 创建订单项
        ProductOrderItemDO orderItemDO = new ProductOrderItemDO();
        // 发送延迟消息
        // 创建PayInfo
        PayInfoVO payInfoVO = new PayInfoVO();

        // 支付结果，这个就是返回由 支付生成的一个html文件
        String payResult = payFactory.pay(payInfoVO);

        if (StringUtils.isNotBlank(payResult)){
            log.info("创建订单成功");
            return AjaxResult.success((Object) payResult);
        }else{
            // 创建订单失败
            log.error("创建订单失败:payInfo={},payResult={}",payInfoVO,payResult);
            return AjaxResult.error("创建失败");
        }
    }


    /**
     * 创建订单
     * 封装 productOrderDO即可
     * @param request
     * @param loginUser
     * @param orderOutTradeNO
     */
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
        productOrderDO.setPayAmount(request.getTotalAmount());
        // 未使用优惠卷的价格
        productOrderDO.setTotalAmount(request.getTotalAmount());
        // 设置订单状态为 new
        productOrderDO.setState(ProductOrderStateEnum.NEW.name());
        // 支付方式
        productOrderDO.setPayType(request.getPayType());
        // 地址
        // 会自动插入 productOrderDO 的 id
        productOrderMapper.insert(productOrderDO);
        return productOrderDO;
    }
}
