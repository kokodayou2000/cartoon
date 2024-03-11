package org.example.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.example.config.AlipayConfig;
import org.example.core.AjaxResult;
import org.example.enums.ProductOrderPayTypeEnum;
import org.example.feign.UserFeignService;
import org.example.service.ProductOrderService;
import org.example.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// 不需要转换成json
@Controller
@RequestMapping("/api/v1/callback/order")
@Slf4j
public class CallBackController {

    @Autowired
    private ProductOrderService productOrderService;


    // 支付宝远程调用该接口
    @PostMapping("/alipay")
    public String alipayCallback(HttpServletRequest request, HttpServletResponse response){

        // 讲异步通知中收到的所有参数存储到map中
        Map<String, String> paramsToMap = convertRequestParamsToMap(request);
        // 支付宝回调通知结果
        log.info("支付宝回调通知结果 {}",paramsToMap);
        try{
            boolean signVerified = AlipaySignature.rsaCheckV1(paramsToMap, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
            if (signVerified){
                // 支付回调，这个接口是通用的
                AjaxResult result = productOrderService.handlerOrderCallbackMsg(ProductOrderPayTypeEnum.ALIPAY,paramsToMap);
                // TODO 创建一个支付记录
                // 记录本次的支付成功
                if (Objects.equals(String.valueOf(result.get("code")), "200")){
                    return "success";
                }
            }
        }catch (AlipayApiException e){
            log.info("支付宝回调验证签名失败: 异常:{}, 参数: {}",e,paramsToMap);
        }

        return "failure";
    }

    private static Map<String,String> convertRequestParamsToMap(HttpServletRequest request){
        HashMap<String, String> paramsMap = new HashMap<>(16);
        Set<Map.Entry<String, String[]>> entries = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int size = values.length;
            if (size == 1){
                paramsMap.put(name,values[0]);
            }else{
                paramsMap.put(name,"");
            }
        }
        System.out.println(paramsMap);
        return paramsMap;
    }
}
