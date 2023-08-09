package com.liuche.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuche.order.model.ProductOrder;
import com.liuche.order.vo.OrderQueryVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 70671
* @description 针对表【product_order】的数据库操作Mapper
* @createDate 2023-08-03 11:43:32
* @Entity generator.domain.ProductOrder
*/
public interface ProductOrderMapper extends BaseMapper<ProductOrder> {

    String queryProductStatus(String outTradeNo);

    int updateStateOrder(@Param("outTradeNo") String outTradeNo,@Param("state") String state);

    int updateOrderByTradeNo(@Param("outTradeNo") String outTradeNo);

    OrderQueryVO queryByPage(Integer page, Integer size);
}




