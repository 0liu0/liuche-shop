package com.liuche.order.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Synchronized;

/**
 * @Author 刘彻
 * @Date 2023/8/7 22:21
 * @PackageName: com.liuche.order.config
 * @ClassName: AliPayConfig
 * @Description: TODO
 */
public class AliPayConfig {
    /**
     * 支付宝网关地址
     */
    public static final String PAY_GATEWAY = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    /**
     * 应用ID
     */
    public static final String APPID = "9021000124667702";
    /**
     * 应用私钥
     */
    public static final String APP_PAI_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCXUY48Oa7aoqPNlQA3XHx4gzVgSl6hCnwNO7r+6Cij4toFvsk1+GxoRuCKryZ9SneHpeh/1nQN+exXp4BvbezQ1smU0Dxbe4b1XP769enamKozcuGt3hIRiakFlhyaEVuIPGA2EsJoShCFHXmMUb8IIqW1jizyHWO7XRIzKsjrLXZxQd0fqdGseL+q49AtEfisjWhsUj2jAN2cfo+ckPK+TwNWm3WXC7cd+wbgkoVowODY+n99pwIg0OtrCdDPNDQUHm3kIjdCbMqlk1gBYSR50IS+wiMoLRpYsKczOUM4kivxcPLeHMJqGR9aWSpiSea1czsqlxrbe1CoJTPiMTGdAgMBAAECggEASOT14xdan2JoDHox73lzUM7MYMWSzntxhSJ4E6IdKc33f3sIDSLOfnFebBCyNvsvl74ZwbO6lp0F+LftTEClK3m9BB5IZT2dpweUoarja/XNUBmQlCR9ictKDDHjN2kTMg77vjDNTFYZnTa0vh8HKtEeCNcj2cLEr5bHVLTQs1GjkiI6rTO4A+tm3jp/fNmjDhIfMC6zBrdLVSBj8sP0pCdRNMZwt2SNwtnlXI0j1BWRlA3kv5Of0bpWChPXEmuhmmV9Tsmr0gcPsU7vQYGGA+xry2xp9AWSbSb3dvuADomPH8B/0V+a2Jz5p/YilgBkQiMkf+QAmX5GeQNoXVQKwQKBgQDr9/jm93QwJ+Zv1Ou2hffd16o/O3K9OWBuL0CoW/8RyklVmTjLhA+mVIfIlAUSFetZsrxi6Zh/g0CS21qJj2e4y8dKEgzPt+8YbTm+FtpwQdXNezSBV9LeSo+JBzBJU8KpCcDkOlBAjdKW9R4tDRwymrzKHqnUo3myOX9zmpYFrQKBgQCkKfvEqHQfe0BRfjSkYb00QTfe1vbxM4wQ1+XbvqURmLGztQD9sUfXFxXxXjvK77Oxaj8cUKdyAYjrlcJTD72onj5H8VOeHXIxMxDvPY+m2hXI92kqB00JwEIdg/+kiHrXzPj7dror9H1UShWMF/EWvhNWf5fvLQc0v8SX8vr5sQKBgEMr887L4mdS9GYhVng+6rBcFDqr0Z3MF4dGLEHA7ntB+l5Dn8dPcKUIqx6OBGOBmd2O49HEbYTe+CLWDo/aIsyYNcMAIGWBFq79aNKT7trF4vb7e5bWSVC+KN35DeXR0jgMbryJXQ8DUE9ot45bjQtawSNIV1AQuTPi6kUeXFaBAoGAUzsB7bvmXAu2GVseP3VaYwLp264rNzEGQ4fifx3SPPCor88GOJlILhIDSwOW1xXeigWvB6EQtCl5DNA53LjvVG0ecIU2+sQ9IBqe7FzO1y5cuy5NAEUBVv9FxgcldnrHpV/DoGKakyx62mBROlztG7W0wjcghd/Tdnf/qXZErpECgYA/7OpLHQZIkZVZo2zRTWmkPpGP30fw6a/Wru0rp00aSzizC5UWpaFN8HQ/AoasQ3SqF3Dqjbfcph6KfL3ufM/LuBA2o4wXsL+xZWxvUoNd83pnnHIwpWuzJVc4bewIG5/AchnyP0dv6SeKwlS48CtJiVFWdyiAtvLx412RhbAFPQ==";
    /**
     * 支付宝公钥
     */
    public static final String ALIPAY_PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqWZ5OZ9zndC/v8UM9a4PTwpcwGMkFmjC2UySbDMYUi31LpTB0dE3RaPuLRmeoZHH8/l6yzVqs67q68AQmMacxrkdIfy7QM6DtWuLqvshl5gcX5EZHmoUs2qX8HLkKSz/Abx/xV1mAUCupw6GkvmoaJwPP2HEwHVbRbeuFQ9uC5/WbN871xEF99e03g5w+exVEiaqOvPa3sX+ZLCCz1V014RY+rlGX9TG6H2yrD5QZa96M4BM9t5vZq/vA4EZ/euux+oTCE27Z+MYVwpt643KS8tdtdL3/pmwfDwsnzGG0tiCj2AxXuMJ6bRFo6EvW8vPueBAC8txNMQjkzZClYjNiwIDAQAB";

    /**
     * 签名类型
     */
    public static final String SIGN_TYPE = "RSA2";
    /**
     * 字符编码
     */
    public static final String CHARSET = "UTF-8";
    public static final String FORMAT = "json";

    private volatile static AlipayClient instance = null;

    private AliPayConfig() {
    }

    /**
     * 单例模式获取，双重校验锁
     */
    public static AlipayClient getInstance() {
        if (instance == null) {
            synchronized (AliPayConfig.class) {
                if (instance == null) {
                    instance = new DefaultAlipayClient(PAY_GATEWAY,APPID, APP_PAI_KEY,FORMAT,CHARSET,ALIPAY_PUB_KEY,SIGN_TYPE);
                }
            }
        }
        return instance;
    }
}