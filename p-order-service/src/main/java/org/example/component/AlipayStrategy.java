package org.example.component;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.config.AlipayConfig;
import org.example.config.PayUrlConfig;
import org.example.enums.BizCodeEnum;
import org.example.enums.ClientType;
import org.example.exceptions.BizException;
import org.example.vo.PayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class AlipayStrategy implements PayStrategy {

    @Autowired
    private PayUrlConfig urlConfig;

    // 统一下单接口
    @Override
    public String unifiedOrder(PayInfoVO payInfoVO) {
        HashMap<String, String> content = new HashMap<>();
        content.put("out_trade_no",payInfoVO.getOutTradeNo());
        content.put("product_code","FAST_INSTANCE_TRADE_PAY");
        // 订单总金额
        content.put("total_amount",payInfoVO.getPayFee().toString());
        content.put("subject",payInfoVO.getTitle());
        content.put("body",payInfoVO.getDescription());
        // 得到单位是分钟
        double timeout = Math.floor(payInfoVO.getOrderPayTimeoutMills())/(1000*60);

        // 前端要判定订单是否要关闭了，如果快要过期，不给二次支付
        if (timeout < 1){
            throw new BizException(BizCodeEnum.PAY_ORDER_PAY_TIMEOUT);
        }
        // 可能用户在支付二维码页面停留过多时间，功能是为了避免逾期
        content.put("timeout_express",Double.valueOf(timeout).intValue()+"m");
        String clientType = payInfoVO.getClientType();
        String form = "";
        try{
            if (clientType.equalsIgnoreCase(ClientType.H5.name())){
                AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
                request.setBizContent(JSON.toJSONString(content));
                request.setNotifyUrl(urlConfig.getAlipayCallBackURL());
                request.setReturnUrl(urlConfig.getAlipaySuccessReturnURL());
                // 发送请求
                AlipayTradeWapPayResponse alipayResponse = AlipayConfig.getInstance().pageExecute(request);
                log.info("响应日志: response={}",alipayResponse);
                if (alipayResponse.isSuccess()){
                    form = alipayResponse.getBody();
                }else {
                    log.error("支付宝构建H5表单失败: response={},payInfo={}",alipayResponse,payInfoVO);
                }
            }else if (clientType.equalsIgnoreCase(ClientType.WEB.name())) {
                // PC 支付
                AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
                request.setBizContent(JSON.toJSONString(content));
                request.setNotifyUrl(urlConfig.getAlipayCallBackURL());
                request.setReturnUrl(urlConfig.getAlipaySuccessReturnURL());
                // 发送请求
                AlipayTradePagePayResponse alipayResponse = AlipayConfig.getInstance().pageExecute(request);
                if (alipayResponse.isSuccess()){
                    form = alipayResponse.getBody();
                }else {
                    log.error("支付宝构建WEB表单失败: response={},payInfo={}",alipayResponse,payInfoVO);
                }
            }
        }catch (AlipayApiException e){
            log.error("支付宝构建表单异常: payInfo={}, exception={}",payInfoVO,e.toString());
        }
        return form;
    }

    @Override
    public String refund(PayInfoVO payInfoVO) {
        return PayStrategy.super.refund(payInfoVO);
    }

    @Override
    public String queryPaySuccess(PayInfoVO payInfoVO) {
        // 查询支付状态
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        HashMap<String,String> content = new HashMap<>();
        // 订单商户号，64位
        content.put("out_trade_no",payInfoVO.getOutTradeNo());
        request.setNotifyUrl(JSON.toJSONString(content));
        AlipayTradeQueryResponse response = null;
        try {
            response = AlipayConfig.getInstance().execute(request);
            log.info("订单查询响应: {}",response.getBody());
        } catch (AlipayApiException e) {
            log.info("支付宝订单查询响应: {}",e.toString());
        }
        if (response != null && response.isSuccess()){
            log.info("支付宝订单状态查询成功: info{}",payInfoVO);
            return response.getTradeStatus();
        }
        return PayStrategy.super.queryPaySuccess(payInfoVO);
    }
}
