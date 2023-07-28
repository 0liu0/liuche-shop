package com.liuche.coupon;

import com.liuche.coupon.model.Coupon;
import com.liuche.coupon.service.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 刘彻
 * @Date 2023/7/28 20:50
 * @PackageName: com.liuche.coupon
 * @ClassName: InitialTest
 * @Description: TODO
 */
@SpringBootTest(classes = {CouponApplication.class})
public class InitialTest {
    @Resource
    private CouponService couponService;
    @Test
    public void test01() {
        List<Coupon> list = couponService.list();
        System.out.println("list:"+list);
    }
}
