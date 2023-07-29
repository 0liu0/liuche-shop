package com.liuche.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CopyUtil;
import com.liuche.coupon.constants.CouponConstant;
import com.liuche.coupon.mapper.CouponMapper;
import com.liuche.coupon.model.Coupon;
import com.liuche.coupon.model.vo.CouponVO;
import com.liuche.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            map.put("total",total);
            map.put("pages",pages);
            map.put("records",voList);
        } catch (Exception e) {
            log.warn("查询优惠券失败，{}",e);
            throw new BusinessException(ExceptionCode.SYSTEM_ERROR,"请稍后再试");
        }
        return map;
    }
}




