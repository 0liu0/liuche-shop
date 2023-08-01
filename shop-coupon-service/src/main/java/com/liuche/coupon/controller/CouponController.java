package com.liuche.coupon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.JsonData;
import com.liuche.coupon.model.vo.CouponVO;
import com.liuche.coupon.service.CouponRecordService;
import com.liuche.coupon.service.CouponService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private CouponRecordService couponRecordService;

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

    @ApiOperation(value = "得到优惠券")
    @GetMapping("/get-coupon/{id}")
    public JsonData getCoupon(@ApiParam("券id") @PathVariable long id) {
        if (id <= 0) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR);
        }
        boolean coupon = couponService.getCoupon(id);
        return coupon?JsonData.ok("领券成功！"):JsonData.error("领券失败");
    }


    @ApiOperation("分页查询我的优惠券列表")
    @GetMapping("/page")
    public JsonData page(@RequestParam(value = "page",defaultValue = "1")int page,
                         @RequestParam(value = "size",defaultValue = "20")int size){

        Map<String,Object> pageInfo = couponRecordService.getPage(page,size);
        return JsonData.ok(pageInfo);
    }

}