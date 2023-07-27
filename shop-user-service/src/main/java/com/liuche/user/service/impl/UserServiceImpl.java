package com.liuche.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CommonUtil;
import com.liuche.common.util.CopyUtil;
import com.liuche.user.constant.RedisConstant;
import com.liuche.user.mapper.UserMapper;
import com.liuche.user.model.User;
import com.liuche.user.model.request.UserRegisterVO;
import com.liuche.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 70671
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-07-23 22:34:07
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean register(UserRegisterVO userRegisterVO, HttpServletRequest request) {
        String code = userRegisterVO.getCode(); // 用户输入的验证码
        String userMail = userRegisterVO.getMail(); // 用户邮箱
        String captchaKey = CommonUtil.getCaptchaKey(request); // key的前缀
        String redisCode = stringRedisTemplate.opsForValue().get(RedisConstant.USER_REGISTER_CODE_MAIL_REDIS_KEY+captchaKey).split("_")[0]; // 用户正确的验证码
        // 校验用户参数
        if (!code.equals(redisCode)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "验证码错误");
        }
        if (findUserMail(userMail)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "该用户已注册");
        }
        // 用于保存用户信息
        User user = CopyUtil.copy(userRegisterVO, User.class);
        user.setSlogan("人生需要动态规划，学习需要贪心算法"); // 默认Slogan
        // 随机生成盐用来加密用户密码
        String salt = "$1$" + CommonUtil.getStringNumRandom(8);
        user.setSecret(salt);
        // 对密码进行加密，生成秘钥
        // 密码 + 加盐处理
        String cryptPwd = Md5Crypt.md5Crypt(userRegisterVO.getPwd().getBytes(), salt);
        user.setPwd(cryptPwd);
        // 对用户信息啥的进行一个初始化 todo
        // 保存用户信息到数据库
        return this.save(user);
    }

    /**
     * 查找该邮箱是否已经注册
     * @param mail
     * @return
     */
    private boolean findUserMail(String mail) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mail", mail);
        User user = this.getOne(wrapper);
        return !ObjectUtils.isEmpty(user);
    }
}




