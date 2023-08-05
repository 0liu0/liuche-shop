package com.liuche.coupon;

import com.liuche.common.model.CouponRecordMessage;
import com.liuche.common.model.ProductMessage;
import com.liuche.coupon.config.MQConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author 刘彻
 * @Date 2023/8/5 18:16
 * @PackageName: com.liuche.coupon
 * @ClassName: MQTest
 * @Description: TODO
 */
@SpringBootTest
@Slf4j
public class MQTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void sendMsg01() {
        rabbitTemplate.convertAndSend("coupon.event.exchange","coupon.release.delay.routing.key","你好，我是帅帅");
    }

    @Test
    public void sendMsg02() {
        CouponRecordMessage message = new CouponRecordMessage();
        message.setOutTradeNo("123456abc");
        message.setTaskId(1L);
        rabbitTemplate.convertAndSend("coupon.event.exchange", "coupon.release.delay.routing.key", message);
    }
}
