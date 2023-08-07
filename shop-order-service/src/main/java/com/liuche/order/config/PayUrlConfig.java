package com.liuche.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 刘彻
 * @Date 2023/8/7 22:55
 * @PackageName: com.liuche.order.config
 * @ClassName: PayUrlConfig
 * @Description: TODO
 */
@Configuration
@Data
public class PayUrlConfig {
    /**
     * 阿里支付成功返回的url
     */
    @Value("${alipay.success_return_url}")
    private String alipaySuccessReturnURL;
    /**
     * 支付成功，回调通知
     */
    @Value("${alipay.callback_url}")
    private String alipayCallbackURL;
}
