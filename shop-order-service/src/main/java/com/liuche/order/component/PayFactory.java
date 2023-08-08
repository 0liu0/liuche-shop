package com.liuche.order.component;

import com.alipay.api.AlipayApiException;
import com.liuche.order.enums.ProductOrderPayTypeEnum;
import com.liuche.order.vo.PayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @Author 刘彻
 * @Date 2023/8/8 16:46
 * @PackageName: com.liuche.order.component
 * @ClassName: PayFactory
 * @Description: 支付的简单工厂，主要作用是直接丢给支付工厂一个PayInfoVO即可让这个工厂对象去判断是那种支付类型传递给Context上下文对象对应的类
 */
@Component
public class PayFactory {
    @Autowired
    @Qualifier(value = "AliPayStrategy")
    private PayStrategy aliPayStrategy;
    @Autowired
    @Qualifier(value = "WeixinPayStrategy")
    private PayStrategy weixinPayStrategy;
    private PayStrategyContext context;

    public String unifiedOrder(PayInfoVO payInfoVO) throws AlipayApiException {
        // 得到用户的支付类型
        String payType = payInfoVO.getPayType();
        // 根据用户选择的类型选择支付类型
        String response = null;

        if (payType.equals(ProductOrderPayTypeEnum.ALIPAY.name())) {
            // 使用支付宝支付
            context = new PayStrategyContext(aliPayStrategy);
            response = context.unifiedOrder(payInfoVO);
        } else if (payType.equals(ProductOrderPayTypeEnum.WECHAT.name())) {
            // 使用微信支付 暂未接入，待完善 todo
            context = new PayStrategyContext(weixinPayStrategy);
            response = context.unifiedOrder(payInfoVO);
        }
        return response;
    }

    public String queryPaySuccess(PayInfoVO payInfoVO) throws AlipayApiException {
        // 得到用户的支付类型
        String payType = payInfoVO.getPayType();
        // 根据用户选择的类型选择支付类型
        String response = null;
        if (payType.equals(ProductOrderPayTypeEnum.ALIPAY.name())) {
            // 使用支付宝支付
            context = new PayStrategyContext(aliPayStrategy);
            response = context.queryPaySuccess(payInfoVO);
        } else if (payType.equals(ProductOrderPayTypeEnum.WECHAT.name())) {
            // 使用微信支付 暂未接入，待完善 todo
            context = new PayStrategyContext(weixinPayStrategy);
            response = context.queryPaySuccess(payInfoVO);
        }
        return response;
    }

}
