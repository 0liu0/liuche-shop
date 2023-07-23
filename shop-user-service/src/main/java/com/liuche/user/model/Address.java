package com.liuche.user.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 电商-公司收发货地址表
 *
 * @TableName address
 */
@TableName(value = "address")
@Data
public class Address implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long user_id;

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @TableField(value = "default_status")
    private Integer default_status;

    /**
     * 收发货人姓名
     */
    @TableField(value = "receive_name")
    private String receive_name;

    /**
     * 收货人电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 省/直辖市
     */
    @TableField(value = "province")
    private String province;

    /**
     * 市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 详细地址
     */
    @TableField(value = "detail_address")
    private String detail_address;

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
    private static final long serialVersionUID = -6838054996968589069L;
}