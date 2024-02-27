package org.example.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.model.OrderMessage;
import org.example.service.ProductOrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = "${mqConfig.order_close_queue}")
public class ProductOrderMQListener {

    @Autowired
    private ProductOrderService productOrderService;

    /**
     * 订单的三种状态 NEW PAY CANCEL
     * 当 notify_url 由于种种原因没有发送请求成功的时候，我们需要重新的向支付宝服务器查询本次订单的交易状态
     * 假如订单状态为 PAY 就不需要向支付宝服务器查询本次订单状态了
     * 如果支付宝那边返回用户已经付款了，就修改支付状态 从 NEW 修改为 PAY
     * 如果查询不到，就说明用户并没有付款，就修改支付状态 从 NEW 修改为 CANCEL
     * @param orderMessage
     * @param message
     * @param channel
     * @throws IOException
     */

    @RabbitHandler
    public void closeProductOrder(OrderMessage orderMessage, Message message, Channel channel) throws IOException {
        log.info("监听到关单消息: closeProductOrder: {} ",orderMessage);

        long msgTag = message.getMessageProperties().getDeliveryTag();
        try{
            boolean flag = productOrderService.closeProductOrder(orderMessage);
            if (flag){
                // 关单成功
                channel.basicAck(msgTag,false);
            }else{
                // 重新入队
                channel.basicReject(msgTag,true);
            }
        }catch (Exception e){
            log.info("定时关单失败 {}", orderMessage);
            channel.basicReject(msgTag,true);
        }

    }
}
