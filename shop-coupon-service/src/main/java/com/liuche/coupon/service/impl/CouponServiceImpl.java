package com.liuche.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CommonUtil;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.RequestContext;
import com.liuche.coupon.constants.CouponConstant;
import com.liuche.coupon.mapper.CouponMapper;
import com.liuche.coupon.model.Coupon;
import com.liuche.coupon.model.CouponRecord;
import com.liuche.coupon.model.vo.CouponVO;
import com.liuche.coupon.service.CouponRecordService;
import com.liuche.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author 70671
 * @description 针对表【coupon】的数据库操作Service实现
 * @createDate 2023-07-28 20:39:52
 */
@Service
@Slf4j
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>
        implements CouponService {
    @Autowired
    private CouponRecordService couponRecordService;

    @Override
    public HashMap<String, Object> getListByPage(int page, int size) {
        List<CouponVO> voList;
        HashMap<String, Object> map = new HashMap<>();
        try {
            Page<Coupon> objectPage = new Page<>(page, size);
            QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("publish", CouponConstant.PUBLISH);
            queryWrapper.eq("category", CouponConstant.PROMOTION);
            queryWrapper.orderByDesc("create_time");
            Page<Coupon> couponPage = this.baseMapper.selectPage(objectPage, queryWrapper);
            List<Coupon> records = couponPage.getRecords();
            voList = CopyUtil.copyList(records, CouponVO.class);
            long total = couponPage.getTotal();
            long pages = couponPage.getPages();
            map.put("total", total);
            map.put("pages", pages);
            map.put("records", voList);
        } catch (Exception e) {
            log.warn("查询优惠券失败，{}", e);
            throw new BusinessException(ExceptionCode.SYSTEM_ERROR, "请稍后再试");
        }
        return map;
    }

    @Override
    @Transactional
    public boolean getCoupon(long id) {
        // 根据优惠券id查询优惠券
        Coupon coupon = this.baseMapper.selectById(id);
        couponCheck(coupon, id); // 校验优惠券信息
        // 记录信息
        CouponRecord couponRecord = new CouponRecord();
        couponRecord.setCouponId(id);
        couponRecord.setCouponTitle(coupon.getCouponTitle());
        couponRecord.setEndTime(coupon.getEndTime());
        couponRecord.setPrice(coupon.getPrice());
        couponRecord.setConditionPrice(coupon.getConditionPrice());
        couponRecord.setStartTime(coupon.getStartTime());
        couponRecord.setUserId(RequestContext.getUserId());
        couponRecord.setUserName(String.valueOf(RequestContext.getUserId())); // 暂时填入用户id
        couponRecord.setUseState(CouponConstant.USER_STATE_NEW);
        int i = this.baseMapper.reduceStock(id, coupon.getStock());
        // 减库存
        if (i != 0) {
            couponRecordService.save(couponRecord);
        } else {
            log.warn("发放优惠券失败:{},用户:{}", coupon, RequestContext.getUserId());
            throw new BusinessException(ExceptionCode.COUPON_NO_STOCK);
        }
        return true;
    }

    private void couponCheck(Coupon coupon, long id) {
        // 判断是否存在这个优惠券
        if (ObjectUtils.isEmpty(coupon)) throw new BusinessException(ExceptionCode.PARAMS_ERROR, "没有此优惠券");
        // 得到用户领取该优惠券的记录次数
        int count = couponRecordService.count(new QueryWrapper<CouponRecord>()
                .eq("user_id", RequestContext.getUserId())
                .eq("coupon_id", id));
        // 判断当前优惠券是否可以领取
        if (coupon.getStock() <= 0 || count >= coupon.getUserLimit()) {
            throw new BusinessException(ExceptionCode.COUPON_NO_STOCK);
        }
        //是否在领取时间范围
        long time = System.currentTimeMillis();
        long start = coupon.getStartTime().getTime();
        long end = coupon.getEndTime().getTime();
        if (time < start || time > end) {
            throw new BusinessException(ExceptionCode.COUPON_OUT_OF_TIME);
        }
    }
}




