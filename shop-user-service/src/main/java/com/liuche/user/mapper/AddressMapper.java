package com.liuche.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuche.user.model.Address;
import org.apache.ibatis.annotations.Param;

/**
* @author 70671
* @description 针对表【address(电商-公司收发货地址表)】的数据库操作Mapper
* @createDate 2023-07-23 22:39:35
* @Entity generator.domain.Address
*/
public interface AddressMapper extends BaseMapper<Address> {

    int updateAddressStatus(@Param("userId") long userId);
}




