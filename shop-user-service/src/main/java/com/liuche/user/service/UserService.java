package com.liuche.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuche.user.model.User;
import com.liuche.user.model.request.UserLoginVO;
import com.liuche.user.model.request.UserRegisterVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 刘彻
* @description 针对表【user】的数据库操作Service
* @createDate 2023-07-23 22:34:07
*/
public interface UserService extends IService<User> {

    boolean register(UserRegisterVO userRegisterVO, HttpServletRequest request);

    String login(UserLoginVO userLoginVO, HttpServletRequest request);
}
