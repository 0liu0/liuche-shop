package com.liuche.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.liuche.common.util.RequestContext;
import com.liuche.coupon.mapper.CouponRecordMapper;
import com.liuche.coupon.model.CouponRecord;
import com.liuche.coupon.service.CouponRecordService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 70671
 * @description 针对表【coupon_record】的数据库操作Service实现
 * @createDate 2023-07-28 20:39:26
 */
@Service
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecord>
        implements CouponRecordService {

    /**
     * 分页查询我的优惠券列表
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> getPage(int page, int size) {
        // 得到用户id
        long userId = RequestContext.getUserId();
        // 根据用胡id查询用户自己所有的优惠券
        Page<CouponRecord> pageInfo = new Page<>(page, size);
        QueryWrapper<CouponRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("create_time");
        Page<CouponRecord> couponRecordPage = this.baseMapper.selectPage(pageInfo, queryWrapper);
        Map<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", couponRecordPage.getTotal());
        pageMap.put("total_page", couponRecordPage.getPages());
        pageMap.put("current_data", couponRecordPage.getRecords());
        return pageMap;
    }
}




