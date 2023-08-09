package com.liuche.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/8/9 16:03
 * @PackageName: com.liuche.order.vo
 * @ClassName: OrderItemQueryVO
 * @Description: TODO
 */
@Data
public class OrderItemQueryVO implements Serializable {
    private static final long serialVersionUID = 261865564566087354L;
    /**
     * 订单项id
     */
    private Long id;

    /**
     * 订单号
     */
    private Long productOrderId;

    /**
     *
     */
    private String outTradeNo;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 购买数量
     */
    private Integer buyNum;

    /**
     * 购物项商品总价格
     */
    private BigDecimal totalAmount;

    /**
     * 购物项商品单价
     */
    private Long amount;

    /**
     * 创建时间
     */
    private Date createTime;
}
