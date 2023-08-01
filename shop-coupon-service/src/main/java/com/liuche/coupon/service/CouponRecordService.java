package com.liuche.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuche.coupon.model.CouponRecord;

import java.util.Map;

/**
* @author 70671
* @description 针对表【coupon_record】的数据库操作Service
* @createDate 2023-07-28 20:39:26
*/
public interface CouponRecordService extends IService<CouponRecord> {

    Map<String, Object> getPage(int page, int size);
}
