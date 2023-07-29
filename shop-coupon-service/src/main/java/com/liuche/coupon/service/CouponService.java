package com.liuche.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuche.coupon.model.Coupon;
import com.liuche.coupon.model.vo.CouponVO;

import java.util.HashMap;
import java.util.List;

/**
* @author 70671
* @description 针对表【coupon】的数据库操作Service
* @createDate 2023-07-28 20:39:52
*/
public interface CouponService extends IService<Coupon> {

    HashMap<String, Object> getListByPage(int page, int size);
}
