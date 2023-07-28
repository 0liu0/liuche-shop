package com.liuche.coupon.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 刘彻
 * @Date 2023/7/28 20:55
 * @PackageName: com.liuche.coupon.controller
 * @ClassName: CouponController
 * @Description: 优惠券的接口
 */
@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {
    @ApiOperation(value = "test测试")
    @GetMapping("/test")
    public String test() {
        return "这是test，加入你看到这串文字就代表后端springboot项目整合成功了!";
    }
}
