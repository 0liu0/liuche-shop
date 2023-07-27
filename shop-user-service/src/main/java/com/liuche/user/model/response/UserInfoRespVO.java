package com.liuche.user.model.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/7/27 14:46
 * @PackageName: com.liuche.user.model.response
 * @ClassName: UserInfoRespVO
 * @Description: 返回给前端的用户数据
 */
@Data
public class UserInfoRespVO {
    private Long id;

    /**
     * 昵称
     */
    private String name;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户签名
     */
    private String slogan;

    /**
     * 0表示女，1表示男
     */
    private Integer sex;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 创建时间
     */
    private Date createTime;
}
