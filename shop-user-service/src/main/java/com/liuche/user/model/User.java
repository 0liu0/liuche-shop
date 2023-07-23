package com.liuche.user.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 密码
     */
    @TableField(value = "pwd")
    private String pwd;

    /**
     * 头像
     */
    @TableField(value = "head_img")
    private String headImg;

    /**
     * 用户签名
     */
    @TableField(value = "slogan")
    private String slogan;

    /**
     * 0表示女，1表示男
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 积分
     */
    @TableField(value = "points")
    private Integer points;

    /**
     * 邮箱
     */
    @TableField(value = "mail")
    private String mail;

    /**
     * 盐，用于个人敏感信息处理
     */
    @TableField(value = "secret")
    private String secret;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 是否已删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private int isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = -8078353908814281038L;
}