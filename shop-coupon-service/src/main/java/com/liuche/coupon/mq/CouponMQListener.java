package com.liuche.coupon.mq;

import com.liuche.common.model.CouponRecordMessage;
import com.liuche.coupon.service.CouponRecordService;
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
 * @Date 2023/8/5 18:18
 * @PackageName: com.liuche.coupon.mq
 * @ClassName: CouponMQListener
 * @Description: TODO
 */
@Component
@RabbitListener(queues = "${mqconfig.coupon_release_queue}")
@Slf4j
public class CouponMQListener {
    @Resource
    private CouponRecordService couponRecordService;

    @RabbitHandler
    public void releaseCouponRecord(String str, Message message, Channel channel) throws IOException {
        log.info("监听到消息：消息内容->{}", str);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
    }

    @RabbitHandler
    public void releaseCouponRecord(CouponRecordMessage msg, Message message, Channel channel) throws IOException {
        log.info("监听到消息：消息内容->{}", msg);
        long tag = message.getMessageProperties().getDeliveryTag();
        // 校验订单状态，执行对应的操作
        boolean flag = couponRecordService.checkOrderOfCoupon(msg);
        try {
            if (flag) {
                channel.basicAck(tag, false);
            } else {
                log.error("释放优惠券失败 flag=false,{}", msg);
                channel.basicReject(tag, true);
            }
        } catch (IOException e) {
            log.error("释放优惠券异常：{}，msg：{}", e, msg);
            channel.basicReject(tag,true);
        }
    }
}
