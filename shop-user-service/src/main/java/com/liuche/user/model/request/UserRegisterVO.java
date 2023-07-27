package com.liuche.user.model.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 刘彻
 * @Date 2023/7/27 12:32
 * @PackageName: com.liuche.user.model.request
 * @ClassName: UserRegisterVO
 * @Description: 用户注册时，填入的信息
 */
@Data
@ApiModel(value ="用户注册参数")
public class UserRegisterVO implements Serializable {
    private static final long serialVersionUID = -2240203371570606640L;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",example = "liuche")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",example = "12345678")
    private String pwd;

    /**
     * 0表示女，1表示男
     */
    @ApiModelProperty(value = "性别",example = "1")
    private Integer sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱",example = "706716852@qq.com")
    private String mail;
    /**
     * 邮箱验证码校验
     */
    @ApiModelProperty(value = "邮箱验证码",example = "123456")
    private String code;
}
