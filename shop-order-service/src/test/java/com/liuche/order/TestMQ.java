package com.liuche.order;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Author 刘彻
 * @Date 2023/8/7 1:09
 * @PackageName: com.liuche.order
 * @ClassName: TestMQ
 * @Description: TODO
 */
@SpringBootTest
public class TestMQ {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    public void Test01() {
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void Test02() {
        rabbitTemplate.convertAndSend("order.event.exchange","order.release.delay.routing.key","你好，我是帅帅");
    }
}
