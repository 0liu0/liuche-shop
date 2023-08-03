package com.liuche.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.math.BigDecimal;


/**
 * @Author 刘彻
 * @Date 2023/8/3 11:58
 * @PackageName: com.liuche.order.dto
 * @ClassName: OrderDTO
 * @Description: 创建订单的对象
 */
@Data
@ApiModel(value = "创建订单对象")
public class OrderDTO {
    /**
     * NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单
     */
    @ApiModelProperty(value = "订单状态",example = "NEW")
    private String state;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额",example = "520")
    private BigDecimal totalAmount;

    /**
     * 订单实际支付价格
     */
    @ApiModelProperty(value = "订单实际支付价格",example = "520")
    private BigDecimal payAmount;

    /**
     * 支付类型，微信-银行-支付宝
     */
    @ApiModelProperty(value = "支付类型，微信-银行-支付宝",example = "WECHAT")
    private String payType;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",example = "大大怪")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像",example = "https://file.xdclass.net/video/2020/alibabacloud/zt-alibabacloud.png")
    private String headImg;

    /**
     * 订单类型 DAILY普通单，PROMOTION促销订单
     */
    @ApiModelProperty(value = "订单类型 DAILY普通单，PROMOTION促销订单",example = "PROMOTION")
    private String orderType;

    /**
     * 收货地址json存储
     */
    @ApiModelProperty(value = "收货地址json存储",example = "火星")
    private String receiverAddress;
}