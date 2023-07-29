package com.liuche.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuche.coupon.model.Coupon;
import org.apache.ibatis.annotations.Param;

/**
* @author 70671
* @description 针对表【coupon】的数据库操作Mapper
* @createDate 2023-07-28 20:39:52
* @Entity generator.domain.Coupon
*/
public interface CouponMapper extends BaseMapper<Coupon> {

    int reduceStock(@Param("couponId") long id,@Param("couponStock") Integer stock);
}




