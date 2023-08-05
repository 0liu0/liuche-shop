package com.liuche.product.mq;

import com.liuche.common.model.ProductMessage;
import com.liuche.product.service.ProductService;
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
 * @Date 2023/8/5 22:19
 * @PackageName: com.liuche.product.mq
 * @ClassName: ProductMQListener
 * @Description: TODO
 */
@Component
@RabbitListener(queues = "${mqconfig.stock_release_queue}")
@Slf4j
public class ProductMQListener {
    @Resource
    private ProductService productService;

    @RabbitHandler
    public void releaseStockRecord(String str, Message message, Channel channel) throws IOException {
        log.info("监听到消息：消息内容->{}", str);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
    }

    @RabbitHandler
    public void releaseStock(ProductMessage msg, Message message, Channel channel) throws IOException, InterruptedException {
        log.info("监听到消息：消息内容->{}", msg);
        long tag = message.getMessageProperties().getDeliveryTag();
        // 校验订单状态，执行对应的操作
        boolean flag = productService.checkOrderOfProduct(msg);
        try {
            if (flag) {
                channel.basicAck(tag, false);
            } else {
                log.error("释放优惠券失败 flag=false,{}", msg);
                Thread.sleep(20);
                channel.basicReject(tag, true);
            }
        } catch (IOException e) {
            Thread.sleep(20);
            log.error("释放优惠券异常：{}，msg：{}", e, msg);
            channel.basicReject(tag,true);
        }
    }

}
