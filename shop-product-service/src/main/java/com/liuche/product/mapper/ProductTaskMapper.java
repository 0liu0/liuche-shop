package com.liuche.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuche.product.model.ProductTask;
import org.apache.ibatis.annotations.Param;

/**
* @author 70671
* @description 针对表【product_task】的数据库操作Mapper
* @createDate 2023-08-05 17:29:56
* @Entity generator.domain.ProductTask
*/
public interface ProductTaskMapper extends BaseMapper<ProductTask> {
    int confirmProduct(@Param("taskId") Long taskId);

    int updateProductTaskCancel(@Param("taskId") Long taskId);
}




