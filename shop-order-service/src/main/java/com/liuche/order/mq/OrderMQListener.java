package com.liuche.order.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author 刘彻
 * @Date 2023/8/7 2:12
 * @PackageName: com.liuche.order.mq
 * @ClassName: OrderMQListener
 * @Description: TODO
 */
@Component
@Slf4j
@RabbitListener(queues="${mqconfig.order_release_queue}")
public class OrderMQListener {
    @RabbitHandler
    public void releaseOrder(String str, Message message, Channel channel) throws IOException {
        log.info("监听到消息：消息内容->{}", str);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
    }
}
