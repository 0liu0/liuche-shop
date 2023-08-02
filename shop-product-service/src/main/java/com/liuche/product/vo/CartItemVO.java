package com.liuche.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author 刘彻
 * @Date 2023/8/2 12:24
 * @PackageName: com.liuche.product.vo
 * @ClassName: CartItemVO
 * @Description: TODO
 */

public class CartItemVO implements Serializable {
    private static final long serialVersionUID = 4013097106599278630L;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 购买数量
     */
    private Integer buyNum;
    /**
     * 商品标题
     */
    private String productTitle;
    /**
     * 商品图片信息
     */
    private String productImg;
    /**
     * 商品单价
     */
    private BigDecimal amount;
    /**
     * 总价格 单价*数量
     */
    private BigDecimal totalAmount;

    public Long getProductId() {
        return productId;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductImg() {
        return productImg;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getTotalAmount() {
        return this.amount.multiply(new BigDecimal(this.buyNum));
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
