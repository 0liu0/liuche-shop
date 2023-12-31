package com.liuche.coupon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuche.common.constants.RedisConstant;
import com.liuche.common.dto.LockCouponRecordDTO;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.common.util.RequestContext;
import com.liuche.coupon.model.CouponRecord;
import com.liuche.coupon.model.vo.CouponRecordVO;
import com.liuche.coupon.model.vo.CouponVO;
import com.liuche.coupon.service.CouponRecordService;
import com.liuche.coupon.service.CouponService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
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

    /**
     * 查询优惠券记录信息
     * 水平权限攻击：也叫作访问控制攻击,Web应用程序接收到用户请求，修改某条数据时，没有判断数据的所属人，
     * 或者在判断数据所属人时从用户提交的表单参数中获取了userid。
     * 导致攻击者可以自行修改userid修改不属于自己的数据
     * @param recordId
     * @return
     */
    @ApiOperation("查询优惠券记录信息")
    @GetMapping("/detail/{record_id}")
    public JsonData findUserCouponRecordById(@PathVariable("record_id")long recordId){
        long userId = RequestContext.getUserId();
        QueryWrapper<CouponRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("coupon_id",recordId);
        CouponRecord couponRecordVO = couponRecordService.getOne(queryWrapper);
        return  couponRecordVO == null? JsonData.buildResult(ExceptionCode.COUPON_NO_EXITS):JsonData.ok(CopyUtil.copy(couponRecordVO, CouponRecordVO.class));
    }

    @ApiOperation("RPC-新用户领券接口")
    @GetMapping("/init-coupon/{userId}")
    public JsonData getInitCoupon(@PathVariable long userId) {
        couponService.initUserCoupon(userId);
        return JsonData.ok("新用户优惠券发送成功！");
    }

    @ApiOperation("RPC-订单服务调用锁定优惠券记录")
    @PostMapping("/lock_records")
    public JsonData lockCouponRecords(@ApiParam("锁定优惠券") @RequestBody LockCouponRecordDTO dto) {
        return couponRecordService.lockCouponRecords(dto);
    }


}