package com.liuche.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.RequestContext;
import com.liuche.user.constant.AddressConstant;
import com.liuche.user.mapper.AddressMapper;
import com.liuche.user.model.Address;
import com.liuche.user.model.request.AddressAddRequest;
import com.liuche.user.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘彻
 * @description 针对表【address(电商-公司收发货地址表)】的数据库操作Service实现
 * @createDate 2023-07-23 22:39:35
 */
@Service
@Slf4j
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
        implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public int addAddress(AddressAddRequest request) {
        long userId = RequestContext.getUserId();
        Address addressDO = CopyUtil.copy(request, Address.class);
        addressDO.setUserId(userId); // 避免不法分子为别人添加一个地址，甚至是默认地址
        // 默认收货地址判断
        if (addressDO.getDefaultStatus() == AddressConstant.DEFAULT_ADDRESS) {
            // 直接将所有地址换成普通收获地址
            int i = addressMapper.updateAddressStatus(userId);
            if (i==1) log.info("修改了默认地址");
        }
        return addressMapper.insert(addressDO);
    }
}




