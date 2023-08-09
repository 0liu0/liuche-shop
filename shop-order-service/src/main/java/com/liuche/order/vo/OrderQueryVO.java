package com.liuche.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author 刘彻
 * @Date 2023/8/9 16:00
 * @PackageName: com.liuche.order.vo
 * @ClassName: OrderQueryVO
 * @Description: 订单信息查询
 */
@Data
public class OrderQueryVO implements Serializable {
    private static final long serialVersionUID = -4041219330272840120L;
    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单唯一标识
     */
    private String outTradeNo;

    /**
     * NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单
     */
    private String state;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单实际支付价格
     */
    private BigDecimal payAmount;

    /**
     * 支付类型，微信-银行-支付宝
     */
    private String payType;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单类型 DAILY普通单，PROMOTION促销订单
     */
    private String orderType;

    /**
     * 收货地址 json存储
     */
    private String receiverAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 订单子项
     */
    List<OrderItemQueryVO> itemList;
}
