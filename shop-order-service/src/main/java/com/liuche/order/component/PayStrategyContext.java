package com.liuche.order.component;

import com.alipay.api.AlipayApiException;
import com.liuche.order.vo.PayInfoVO;
import org.springframework.stereotype.Component;

/**
 * @Author 刘彻
 * @Date 2023/8/8 16:33
 * @PackageName: com.liuche.order.component
 * @ClassName: PayStrategyContext
 * @Description: TODO
 */
public class PayStrategyContext {
    private final PayStrategy payStrategy;
    public PayStrategyContext(PayStrategy payStrategy) {
        this.payStrategy = payStrategy;
    }

    /**
     * 下单业务
     * @param payInfoVO
     * @return
     */
    public String unifiedOrder(PayInfoVO payInfoVO) throws AlipayApiException {
        return payStrategy.unifiedOrder(payInfoVO);
    }

    /**
     * 查询是否成功业务
     * @param payInfoVO
     * @return
     */
    public String queryPaySuccess(PayInfoVO payInfoVO) throws AlipayApiException {
        return payStrategy.queryPaySuccess(payInfoVO);
    }


}
