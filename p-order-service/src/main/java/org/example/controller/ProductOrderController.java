package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.config.PayUrlConfig;
import org.example.core.AjaxResult;
import org.example.request.ChargeReq;
import org.example.request.UserBuyChapterRequest;
import org.example.service.ProductOrderService;
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
     * @return
     */
    @PostMapping("/userBuy")
    public AjaxResult userBuy(
            @RequestBody UserBuyChapterRequest req
    ){
        // 确认订单
        return orderService.userBuy(req);
    }

    /**
     * 订单状态
     */
    @GetMapping("/orderState/{orderNO}")
    public AjaxResult orderState(
            @PathVariable("orderNO") String orderNO
    ){
        return orderService.orderState(orderNO);
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

