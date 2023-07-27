package com.liuche.user.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 刘彻
 * @Date 2023/7/27 14:30
 * @PackageName: com.liuche.user.model.request
 * @ClassName: UserLoginVO
 * @Description: 用户登录基本信息
 */
@Data
public class UserLoginVO implements Serializable {
    private static final long serialVersionUID = -9059316340914971577L;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "12345678")
    private String pwd;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "706716852@qq.com")
    private String mail;
    /**
     * 图形验证码校验
     */
    @ApiModelProperty(value = "邮箱验证码", example = "123456")
    private String code;
}
