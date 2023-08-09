package com.liuche.order.mq;

import com.liuche.common.model.OrderMessage;
import com.liuche.order.service.ProductOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
@RabbitListener(queues = "${mqconfig.order_release_queue}")
public class OrderMQListener {
    @Resource
    private ProductOrderService productOrderService;

//    @RabbitHandler
//    public void releaseOrderStr(String str, Message message, Channel channel) throws IOException {
//        log.info("监听到消息：消息内容->{}", str);
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        channel.basicAck(deliveryTag, false);
//    }

    @RabbitHandler
    public void releaseOrder(OrderMessage msg, Message message, Channel channel) throws IOException {
        log.info("监听到消息：消息内容->{}", msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            if (msg == null) {
                log.warn("消息体没有内容哦");
                channel.basicAck(deliveryTag, false);
            } else {
                log.info("恭喜客官来到这一层");
                boolean flag = productOrderService.checkOrderMessage(msg);
                if (flag) {
                    channel.basicAck(deliveryTag, false);
                } else {
                    channel.basicReject(deliveryTag, true);
                }
            }
        } catch (Exception e) {
            // 把记录打印在数据库中方便后续排查 todo
            log.error("出现异常:{}", e);
            channel.basicAck(deliveryTag, false);
        }
    }
}