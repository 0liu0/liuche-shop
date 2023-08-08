package com.liuche.order.component;

import com.alipay.api.AlipayApiException;
import com.liuche.order.vo.PayInfoVO;

/**
 * @Author 刘彻
 * @Date 2023/8/8 16:25
 * @PackageName: com.liuche.order.component
 * @ClassName: PayStrategy
 * @Description: 调用支付应有的方法
 */
public interface PayStrategy {
    /**
     * 下单
     * @param payInfoVO
     * @return
     */
    String unifiedOrder(PayInfoVO payInfoVO) throws AlipayApiException;

    /**
     * 退款
     * @param payInfoVO
     * @return
     */
    default String refund(PayInfoVO payInfoVO){return null;}

    /**
     * 查询当前订单是否成功
     * @param payInfoVO
     * @return
     */
    String queryPaySuccess(PayInfoVO payInfoVO) throws AlipayApiException;
}
