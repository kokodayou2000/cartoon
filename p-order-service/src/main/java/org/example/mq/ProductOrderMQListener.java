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
     * 死信处理器
     * 关键点：
     * 消息重复消费，幂等性保证
     * 并发状态下如何保证安全
     * @param orderMessage
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void closeProductOrder(OrderMessage orderMessage, Message message, Channel channel) throws IOException {
        log.info("监听到关单消息: closeProductOrder: {} ",orderMessage);
        // 消息标签
        // TODO 支付宝校验订单是否支付成功失败
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
