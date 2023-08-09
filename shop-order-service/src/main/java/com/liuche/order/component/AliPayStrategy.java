package com.liuche.order.component;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.order.config.AliPayConfig;
import com.liuche.order.config.PayUrlConfig;
import com.liuche.order.vo.PayInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

/**
 * @Author 刘彻
 * @Date 2023/8/8 16:29
 * @PackageName: com.liuche.order.component
 * @ClassName: AliPayStrategy
 * @Description: 支付宝支付具体实现类
 */
@Service("AliPayStrategy")
@Slf4j
public class AliPayStrategy implements PayStrategy{
    @Autowired
    private PayUrlConfig payUrlConfig;
    /**
     * 阿里下单操作
     * @param payInfoVO
     * @return
     */
    @Override
    public String unifiedOrder(PayInfoVO payInfoVO) throws AlipayApiException {
        // 得到商品剩余支付时间，查看该订单是否还可以支付。有一种情景：用户创建了订单之后迟迟未支付，用户就卡在支付的页面上时间一过再点支付，
        // 此时消息中间件已经取消了该订单就会导致数据不一致的情况。此时应该禁止该用户继续支付。
        Long timeoutMills = payInfoVO.getOrderPayTimeoutMills();
        if (System.currentTimeMillis()>timeoutMills) throw new BusinessException(ExceptionCode.ORDER_OUT_OF_PAY_TIME);
        HashMap<Object, Object> content = new HashMap<>();
        //商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
        log.info("订单号:{}",payInfoVO.getOutTradeNo());
        content.put("out_trade_no", payInfoVO.getOutTradeNo());
        content.put("product_code", "FAST_INSTANT_TRADE_PAY");
        //订单总金额，单位为元，精确到小数点后两位
        content.put("total_amount", String.valueOf(payInfoVO.getPayFee()));
        //商品标题/交易标题/订单标题/订单关键字等。 注意：不可使用特殊字符，如 /，=，&amp; 等。
        content.put("subject", payInfoVO.getTitle());
        //商品描述，可空
        content.put("body", payInfoVO.getDescription());
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        content.put("timeout_express", "5m");
        String form = null;
        try {
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayClient alipayClient = AliPayConfig.getInstance();
            request.setBizContent(JSON.toJSONString(content));
            request.setNotifyUrl(payUrlConfig.getAlipayCallbackURL());
            request.setReturnUrl(payUrlConfig.getAlipaySuccessReturnURL());
            AlipayTradePagePayResponse responseAli = alipayClient.pageExecute(request);
            if(responseAli.isSuccess()){
                System.out.println("调用成功");
                form = responseAli.getBody();
            } else {
                log.info("调用失败");
                throw new BusinessException(ExceptionCode.ORDER_INIT_FAILED);
            }
        } catch (AlipayApiException e) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR);
        }
        return form;
    }

    /**
     * 阿里查询订单操作
     * @param payInfoVO
     * @return
     */
    @Override
    public String queryPaySuccess(PayInfoVO payInfoVO) throws AlipayApiException {
        AlipayClient alipayClient = AliPayConfig.getInstance();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        HashMap<Object, Object> content = new HashMap<>();
        content.put("out_trade_no",payInfoVO.getOutTradeNo());
        request.setBizContent(JSON.toJSONString(content));
        AlipayTradeQueryResponse response;
        try {
            response = alipayClient.execute(request);
            if(response.isSuccess()){
                log.info("成功信息：{}",response.getBody());
            } else {
                log.info("失败信息：{}",response.getBody());
                return null;
            }
        } catch (AlipayApiException e) {
            throw new BusinessException(ExceptionCode.ORDER_MESSAGE_QUERY_ERROR);
        }
        return response.getTradeStatus();
    }
}
