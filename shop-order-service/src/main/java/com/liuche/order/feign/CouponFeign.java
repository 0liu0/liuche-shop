package com.liuche.order.feign;

import com.liuche.common.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 刘彻
 * @Date 2023/8/6 20:56
 * @PackageName: com.liuche.order.feign
 * @ClassName: CouponFeign
 * @Description: TODO
 */
@FeignClient("shop-coupon-service")
public interface CouponFeign {
    @GetMapping("/api/v1/coupon/detail/{record_id}")
    JsonData findUserCouponRecordById(@PathVariable("record_id")long recordId);
}
