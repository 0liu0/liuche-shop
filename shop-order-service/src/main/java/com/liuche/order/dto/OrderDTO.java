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
     * 订单优惠券id
     */
    @ApiModelProperty(value = "订单优惠券id",example = "[1]")
    private Long[] couponRecordIds;
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id",example = "[1]")
    private Long[] productIds;
    /**
     * 付款方式
     */
    @ApiModelProperty(value = "付款方式",example = "ALIPAY")
    private String payType;
    /**
     * 付款环境 H5/APP
     */
    @ApiModelProperty(value = "付款环境 H5/APP",example = "H5")
    private String clientType;
    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "地址ID",example = "1")
    private Long addressId;
    /**
     * 总该付款的价格
     */
    @ApiModelProperty(value = "总该付款的价格",example = "520")
    private BigDecimal totalAmount;
    /**
     * 用户实际付款的价格
     */
    @ApiModelProperty(value = "用户实际付款的价格",example = "520")
    private BigDecimal realPayAmount;
}