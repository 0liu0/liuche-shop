package com.liuche.user.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/7/28 18:13
 * @PackageName: com.liuche.user.model.request
 * @ClassName: AddressAddRequest
 * @Description: 新增地址信息
 */
@Data
@ApiModel(value = "新增收货地址的基本信息")
public class AddressAddRequest implements Serializable {
    private static final long serialVersionUID = 1530746422262359556L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", example = "5")
    private Long userId;

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @ApiModelProperty(value = "1为设置为默认地址；0为普通地址", example = "1")
    private Integer defaultStatus;

    /**
     * 收发货人姓名
     */
    @ApiModelProperty(value = "收货人姓名", example = "刘祖彻")
    private String receiveName;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "联系人电话", example = "15271167085")
    private String phone;

    /**
     * 省/直辖市
     */
    @ApiModelProperty(value = "省/直辖市", example = "湖北省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市", example = "武汉市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区", example = "洪山区")
    private String region;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址",example = "东湖城二期14栋二单元303")
    private String detailAddress;
}
