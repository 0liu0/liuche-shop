package com.liuche.user.feign.coupon;

import com.liuche.common.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 刘彻
 * @Date 2023/8/3 13:11
 * @PackageName: com.liuche.user.feign.coupon
 * @ClassName: CouponFeign
 * @Description: TODO
 */
@FeignClient(name = "shop-coupon-service")
public interface CouponFeign {
    @GetMapping("/api/v1/coupon/init-coupon/{userId}")
    JsonData getInitCoupon(@PathVariable long userId);
}
