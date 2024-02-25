package org.example.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
public class RabbitMQConfig {

    /**
     * 交换机
     */
    @Value("${mqConfig.order_event_exchange}")
    private String eventExchange;


    /**
     * 第一个队列延迟队列，
     */
    @Value("${mqConfig.order_close_delay_queue}")
    private String orderCloseDelayQueue;

    /**
     * 第一个队列的路由key
     * 进入队列的路由key
     */
    @Value("${mqConfig.order_close_delay_routing_key}")
    private String orderCloseDelayRoutingKey;


    /**
     * 第二个队列，被监听恢复库存的队列
     */
    @Value("${mqConfig.order_close_queue}")
    private String orderCloseQueue;

    /**
     * 第二个队列的路由key
     * 即进入死信队列的路由key
     */
    @Value("${mqConfig.order_close_routing_key}")
    private String orderCloseRoutingKey;

    /**
     * 过期时间
     */
    @Value("${mqConfig.ttl}")
    private Integer ttl;

    // 消息转换器
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    // 自动创建交换机
    @Bean
    public Exchange orderEventExchange(){
        // 持久化，自动删除
        return new TopicExchange(eventExchange,true,false);
    }
    // 配置延迟队列
    @Bean
    public Queue orderCloseDelayQueue(){
        // 这些参数可以在rabbitMQ客户端中看到
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",ttl);
        args.put("x-dead-letter-routing-key", orderCloseRoutingKey);
        args.put("x-dead-letter-exchange",eventExchange);
        return new Queue(orderCloseDelayQueue,true,false,false,args);
    }

    // 配置死信队列
    @Bean
    public Queue orderCloseQueue(){
        return new Queue(orderCloseQueue,true,false,false);
    }

    /**
     * 延迟队列绑定关系
     * @return
     */
    @Bean
    public Binding orderCloseDelayBinding(){
        return new Binding(
                orderCloseDelayQueue, // 延迟队列
                Binding.DestinationType.QUEUE, // 绑定类型
                eventExchange, // 要绑定的交换机
                orderCloseDelayRoutingKey, // 路由key
                null
        );
    }

    /**
     * 死信队列绑定关系
     * @return
     */
    @Bean
    public Binding orderCloseBinding(){
        return new Binding(
                orderCloseQueue, // 死信队列
                Binding.DestinationType.QUEUE, // 绑定类型
                eventExchange, // 要绑定的交换机
                orderCloseRoutingKey, // 路由key
                null
        );
    }


}
