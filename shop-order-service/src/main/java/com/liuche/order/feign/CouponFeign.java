package com.liuche.order.feign;

import com.liuche.common.dto.LockCouponRecordDTO;
import com.liuche.common.dto.LockProductDTO;
import com.liuche.common.util.JsonData;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("/api/v1/coupon/lock_records")
    JsonData lockCouponRecords(@ApiParam("锁定优惠券") @RequestBody LockCouponRecordDTO dto);
}
