package com.liuche.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.liuche.user.mapper.UserMapper;
import com.liuche.user.model.User;
import com.liuche.user.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 70671
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-07-23 22:34:07
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




