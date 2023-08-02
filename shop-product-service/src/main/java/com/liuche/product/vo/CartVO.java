package com.liuche.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author 刘彻
 * @Date 2023/8/2 12:24
 * @PackageName: com.liuche.product.vo
 * @ClassName: CartVO
 * @Description: TODO
 */
public class CartVO implements Serializable {
    private static final long serialVersionUID = 2891390748484463597L;
    /**
     * 购物项
     */
    private List<CartItemVO> cartItems;
    /**
     * 购买总件数
     */
    private Integer totalNum;
    /**
     * 购物车总价格
     */
    private BigDecimal totalPrice;
    /**
     * 实际支付总价格
     */
    private BigDecimal realPayPrice;

    public List<CartItemVO> getCartItems() {
        return cartItems;
    }

    public Integer getTotalNum() {
        int account;
        if (cartItems == null) return 0;
        account = cartItems.stream().mapToInt(CartItemVO::getBuyNum).sum();
        return account;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal account = new BigDecimal("0");
        if (cartItems == null) return account;
        for (CartItemVO cartItem : this.cartItems) {
            BigDecimal totalAmount = cartItem.getTotalAmount();
            account = account.add(totalAmount);
        }
        return account;
    }

    public BigDecimal getRealPayPrice() {
        return this.getTotalPrice();
    }

    public void setCartItems(List<CartItemVO> cartItems) {
        this.cartItems = cartItems;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setRealPayPrice(BigDecimal realPayPrice) {
        this.realPayPrice = realPayPrice;
    }
}
