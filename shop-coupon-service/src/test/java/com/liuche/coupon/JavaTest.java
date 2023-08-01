package com.liuche.coupon;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @Author 刘彻
 * @Date 2023/7/30 15:36
 * @PackageName: com.liuche.coupon
 * @ClassName: JavaTest
 * @Description: TODO
 */
@SpringBootTest
public class JavaTest {
    @Test
    public void test01() {
        short s1 = 1;
        s1 += 1;
        System.out.println("s1:" + s1);
        char name = '刘';
        System.out.println("name:" + name);
        Vector<Object> objects = new Vector<>();
        LinkedList<Object> list = new LinkedList<>();
        HashSet<Object> hashSet = new HashSet<>();
        Class clz = int[].class;
        System.out.println(clz.getSuperclass().getName());
        double money = 1.2;
        money = (float) (money+2.6);
        System.out.println("money:"+money);
        System.out.println(10000==10000);
    }
}
