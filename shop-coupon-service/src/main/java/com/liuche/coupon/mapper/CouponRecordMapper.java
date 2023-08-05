package com.liuche.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuche.coupon.model.CouponRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 70671
* @description 针对表【coupon_record】的数据库操作Mapper
* @createDate 2023-07-28 20:39:26
* @Entity generator.domain.CouponRecord
*/
public interface CouponRecordMapper extends BaseMapper<CouponRecord> {

    int updateUseStateByIdBatch(@Param("recordIdList") List<Long> recordIdList,@Param("userId") Long userId,@Param("outTradeNo") String outTradeNo);

    int releaseCoupon(@Param("couponRecordId") Long couponRecordId);
}




