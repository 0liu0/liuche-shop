package com.liuche.coupon.controller;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.JsonData;
import com.liuche.coupon.model.vo.CouponVO;
import com.liuche.coupon.service.CouponService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private CouponService couponService;
    @ApiOperation(value = "test测试")
    @GetMapping("/test")
    public String test() {
        return "这是test，加入你看到这串文字就代表后端springboot项目整合成功了!";
    }

    @ApiOperation(value = "分页查询优惠券")
    @GetMapping("/list/{page}/{size}")
    public JsonData list(@PathVariable int page, @PathVariable int size) {
        if (page <= 0 && size <= 0) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR);
        }
        HashMap<String, Object> listByPage = couponService.getListByPage(page, size);
        return JsonData.ok(listByPage);
    }
}