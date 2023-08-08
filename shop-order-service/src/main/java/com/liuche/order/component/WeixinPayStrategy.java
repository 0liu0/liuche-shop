package com.liuche.order.component;

import com.liuche.order.vo.PayInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author 刘彻
 * @Date 2023/8/8 16:31
 * @PackageName: com.liuche.order.component
 * @ClassName: WeixinPayStrategy
 * @Description: 微信支付的具体实现类
 */
@Service("WeixinPayStrategy")
@Slf4j
public class WeixinPayStrategy implements PayStrategy{
    /**
     * 下单
     * @param payInfoVO
     * @return
     */
    @Override
    public String unifiedOrder(PayInfoVO payInfoVO) {
        return null;
    }

    /**
     * 查询是否支付成功
     * @param payInfoVO
     * @return
     */
    @Override
    public String queryPaySuccess(PayInfoVO payInfoVO) {
        return null;
    }
}
