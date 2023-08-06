package com.liuche.product;

import com.liuche.common.model.ProductMessage;
import com.liuche.product.config.MQConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * @Author 刘彻
 * @Date 2023/8/5 22:22
 * @PackageName: com.liuche.product
 * @ClassName: MQTest
 * @Description: TODO
 */
@SpringBootTest
@Slf4j
public class MQTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MQConfig mqConfig;
    @Test
    public void sendMsg() {
        rabbitTemplate.convertAndSend("stock.event.exchange","stock.release.delay.routing.key","你好啊 大蟀蟀！");
    }
    @Test
    public void sendMsg01() {
        // 发送MQ延迟消息 ，解锁商品库存 taskId
        ProductMessage productMessage = new ProductMessage();
        productMessage.setTaskId(1L);
        productMessage.setOutTradeNo("123456abc");
        productMessage.setUserId(5L);
        rabbitTemplate.convertAndSend(mqConfig.getEventExchange(),mqConfig.getStockReleaseDelayRoutingKey(),productMessage);
        log.info("商品库存锁定信息发送成功:{}",productMessage);
    }
    @Test
    public void testBigDecimal() {
        BigDecimal a = BigDecimal.ZERO;
        BigDecimal b = BigDecimal.valueOf(0.01);
        System.out.println(a.compareTo(b));
    }
}
