package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.example.config.PayUrlConfig;
import org.example.core.AjaxResult;
import org.example.request.ChargeReq;
import org.example.request.ConfirmOrderRequest;
import org.example.request.UserChargeReq;
import org.example.service.ProductOrderService;
import org.example.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class ProductOrderController {

    @Autowired
    private ProductOrderService orderService;

    @Autowired
    private PayUrlConfig payUrlConfig;

    @GetMapping("/test")
    public String test() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 用户充值
     * @param req 用户id和充值金额
     */
    @PostMapping("/charge")
    public void charge(
            @RequestBody ChargeReq req,
            HttpServletResponse response
            ){
        // 确认订单
        AjaxResult jsonData  = orderService.charge(req);

        writeData(response,jsonData);
    }

    /**
     * 用户购买漫画的章节
     * @param req 购买的章节列表，漫画id，总金额，支付方式等数据
     * @param response 响应数据
     * @return
     */
    @PostMapping("/confirm")
    public void confirm(
            @RequestBody ConfirmOrderRequest req,
            HttpServletResponse response
    ){
        // 确认订单
        AjaxResult result = orderService.confirmOrder(req);

        writeData(response,result);
    }

    /**
     * 订单状态
     */
    @GetMapping("/orderState/{orderNO}")
    public AjaxResult orderState(
            @PathVariable("orderNO") String orderNO
    ){
        return AjaxResult.error("");
    }




    // 写入数据
    private void writeData(HttpServletResponse response, AjaxResult ajaxResult) {
        try{

            response.setContentType("text/html;charset=UTF8");
            // 设置返回体 这个会出现 中文字符乱码
            response.getWriter().write((String)ajaxResult.get("msg"));
            // 刷新
            response.getWriter().flush();
        }catch (IOException e){
            log.error("响应异常 {}",e.toString());
        }finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                log.error("关闭响应流异常 {}",e.toString());
            }
        }
    }

}

