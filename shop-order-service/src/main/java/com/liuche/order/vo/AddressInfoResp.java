package com.liuche.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/7/28 19:54
 * @PackageName: com.liuche.user.model.response
 * @ClassName: AddressInfoResp
 * @Description: TODO
 */
@Data
@ToString
public class AddressInfoResp {
    /**
     * 地址专属id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @TableField(value = "default_status")
    private Integer defaultStatus;

    /**
     * 收发货人姓名
     */
    @TableField(value = "receive_name")
    private String receiveName;

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
    private String detailAddress;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
}
