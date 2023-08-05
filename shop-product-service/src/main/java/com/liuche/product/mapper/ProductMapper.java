package com.liuche.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuche.product.model.Product;
import org.apache.ibatis.annotations.Param;

/**
* @author 70671
* @description 针对表【product】的数据库操作Mapper
* @createDate 2023-08-01 22:23:53
* @Entity generator.domain.Product
*/
public interface ProductMapper extends BaseMapper<Product> {

    int lockStock(@Param("id") Long id, @Param("buyNum") int buyNum);

    int releaseProduct(@Param("productId") Long productId,@Param("buyNum") Integer buyNum);
}




