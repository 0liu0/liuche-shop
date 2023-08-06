package com.liuche.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
    @Test
    public void Test01() {
        BigDecimal a = new BigDecimal("3");
        BigDecimal b = new BigDecimal("3");
        System.out.println(a.add(b));
    }
}
