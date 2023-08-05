package com.liuche.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuche.coupon.model.CouponTask;
import org.apache.ibatis.annotations.Param;

/**
* @author 70671
* @description 针对表【coupon_task】的数据库操作Mapper
* @createDate 2023-08-05 17:29:46
* @Entity generator.domain.CouponTask
*/
public interface CouponTaskMapper extends BaseMapper<CouponTask> {

    int confirmRecord(@Param("taskId") Long taskId);

    int updateRecordTaskCancel(Long taskId);
}




