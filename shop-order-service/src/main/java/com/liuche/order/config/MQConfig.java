package com.liuche.order.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Author 刘彻
 * @Date 2023/8/5 17:23
 * @PackageName: com.liuche.order.config
 * @ClassName: MQConfig
 * @Description: mq配置类
 */
@Data
@Configuration
public class MQConfig {
    /**
     * 交换机
     */
    @Value("${mqconfig.order_event_exchange}")
    private String eventExchange;


    /**
     * 第一个队列延迟队列，
     */
    @Value("${mqconfig.order_release_delay_queue}")
    private String orderReleaseDelayQueue;

    /**
     * 第一个队列的路由key
     * 进入队列的路由key
     */
    @Value("${mqconfig.order_release_delay_routing_key}")
    private String orderReleaseDelayRoutingKey;


    /**
     * 第二个队列，被监听恢复库存的队列
     */
    @Value("${mqconfig.order_release_queue}")
    private String orderReleaseQueue;

    /**
     * 第二个队列的路由key
     * 即进入死信队列的路由key
     */
    @Value("${mqconfig.order_release_routing_key}")
    private String orderReleaseRoutingKey;

    /**
     * 过期时间
     */
    @Value("${mqconfig.ttl}")
    private Integer ttl;

    /**
     * 创建消息转换器
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 创建交换机
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(eventExchange, true, false);
    }

    /**
     * 创建队列
     */
    // 延迟队列，用于把消息传给死信队列，由死信队列进行消费
    @Bean
    public Queue delayQueue() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", ttl);
        args.put("x-dead-letter-routing-key", orderReleaseRoutingKey);
        args.put("x-dead-letter-exchange", eventExchange);
        return new Queue(orderReleaseDelayQueue, false, false, true, args);
    }

    // 死信队列，用于被监听消费
    @Bean
    public Queue releaseQueue() {
        return new Queue(orderReleaseQueue, false, false, true);
    }

    /**
     * 绑定交换机
     */
    @Bean
    public Binding orderReleaseDelayBinding() {
        return new Binding(orderReleaseDelayQueue, Binding.DestinationType.QUEUE, eventExchange, orderReleaseDelayRoutingKey, null);
    }

    // 死信队列绑定交换机
    @Bean
    public Binding orderReleaseBinding() {
        return new Binding(orderReleaseQueue, Binding.DestinationType.QUEUE, eventExchange, orderReleaseRoutingKey, null);
    }
}
