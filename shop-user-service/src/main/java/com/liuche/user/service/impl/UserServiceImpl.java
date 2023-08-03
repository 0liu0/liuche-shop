package com.liuche.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CommonUtil;
import com.liuche.common.util.CopyUtil;
import com.liuche.user.constant.RedisConstant;
import com.liuche.user.feign.coupon.CouponFeign;
import com.liuche.user.mapper.UserMapper;
import com.liuche.user.model.User;
import com.liuche.user.model.request.UserLoginVO;
import com.liuche.user.model.request.UserRegisterVO;
import com.liuche.user.service.UserService;
import com.liuche.common.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @Resource
    private CouponFeign couponFeign;

    @Override
    public boolean register(UserRegisterVO userRegisterVO, HttpServletRequest request) {
        String code = userRegisterVO.getCode(); // 用户输入的验证码
        String userMail = userRegisterVO.getMail(); // 用户邮箱
        String redisCode = null; // 用户正确的验证码
        try {
            String captchaKey = CommonUtil.getCaptchaKey(request); // key的前缀
            redisCode = stringRedisTemplate.opsForValue().get(RedisConstant.USER_REGISTER_CODE_MAIL_REDIS_KEY + captchaKey).split("_")[0];
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "验证码已过期");
        }
        // 校验用户参数
        if (!code.equals(redisCode)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "验证码错误");
        }
        if (!ObjectUtils.isEmpty(findUserMail(userMail))) {
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
        // 保存用户信息到数据库
        boolean save = this.save(user);
        // 保存到数据库中后user变量会有一个userId
        // 对用户信息啥的进行一个初始化 todo
        if (save) {
            couponFeign.getInitCoupon(user.getId());
        }else {
            throw new BusinessException(ExceptionCode.SYSTEM_ERROR);
        }
        return true;
    }

    @Override
    public String login(UserLoginVO userLoginVO, HttpServletRequest request) {
        // 得到基本信息
        String code = userLoginVO.getCode();
        String mail = userLoginVO.getMail();
        String pwd = userLoginVO.getPwd();
        // 校验参数
        // 得到图形验证码的值
        User user;
        String redisCode; // 用户正确的验证码
        try {
            String captchaKey = CommonUtil.getCaptchaKey(request); // key的前缀
            redisCode = stringRedisTemplate.opsForValue().get(RedisConstant.USER_LOGIN_CODE_IMG_REDIS_KEY + captchaKey).split("_")[0];
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "验证码已过期");
        }
        if (!code.equals(redisCode)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "验证码错误");
        }
        user = findUserMail(mail);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "该用户未注册");
        }
        // 得到用户密码的加密值
        String cryptPwd = Md5Crypt.md5Crypt(pwd.getBytes(), user.getSecret());
        if (!cryptPwd.equals(user.getPwd())) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "用户名或密码不正确");
        }
        com.liuche.common.model.User user1 = CopyUtil.copy(user, com.liuche.common.model.User.class);
        return JWTUtil.geneJsonWebToken(user1, request); // 返回前端token信息
    }

    /**
     * 查找该邮箱是否已经注册
     *
     * @param mail
     * @return
     */
    private User findUserMail(String mail) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mail", mail);
        return this.getOne(wrapper);
    }
}




