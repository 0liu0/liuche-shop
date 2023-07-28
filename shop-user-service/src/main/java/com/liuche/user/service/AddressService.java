package com.liuche.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.liuche.user.model.Address;
import com.liuche.user.model.request.AddressAddRequest;

/**
* @author 70671
* @description 针对表【address(电商-公司收发货地址表)】的数据库操作Service
* @createDate 2023-07-23 22:39:35
*/
public interface AddressService extends IService<Address> {

    int addAddress(AddressAddRequest request);
}
