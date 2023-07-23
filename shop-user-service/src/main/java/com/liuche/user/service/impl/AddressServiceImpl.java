package com.liuche.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.user.mapper.AddressMapper;
import com.liuche.user.model.Address;
import com.liuche.user.service.AddressService;
import org.springframework.stereotype.Service;

/**
* @author 刘彻
* @description 针对表【address(电商-公司收发货地址表)】的数据库操作Service实现
* @createDate 2023-07-23 22:39:35
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService {

}




