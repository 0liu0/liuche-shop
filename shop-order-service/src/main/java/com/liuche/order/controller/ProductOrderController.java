package com.liuche.order.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.liuche.common.util.JsonData;
import com.liuche.order.config.AliPayConfig;
import com.liuche.order.config.PayUrlConfig;
import com.liuche.order.dto.OrderDTO;
import com.liuche.order.service.ProductOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


/**
 * @Author 刘彻
 * @Date 2023/8/3 11:51
 * @PackageName: com.liuche.order.controller
 * @ClassName: ProductOrderController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class ProductOrderController {
    @Resource
    private ProductOrderService productOrderService;
    @Resource
    private PayUrlConfig payUrlConfig;
    @PostMapping("/add")
    public JsonData addOrder(@RequestBody OrderDTO dto) {
        boolean flag = productOrderService.confirmOrder(dto);
        if (flag) { // 创建订单成功
            // 根据支付方式类型转到合适的支付
        }else { // 创建订单失败
            // 返回给前端创建失败
        }
        return JsonData.ok("订单成功生成！");
    }
    @GetMapping("/query_state/{outTradeNo}")
    public JsonData queryState(@PathVariable String outTradeNo) {
        // 根据ID查询订单状态
        String status = productOrderService.queryProductStatus(outTradeNo);
        return JsonData.ok(status);
    }

    @ApiOperation("模拟发送支付信息")
    @GetMapping("/test/pay")
    public void testPay(HttpServletResponse response) throws AlipayApiException, IOException {
        HashMap<Object, Object> content = new HashMap<>();
        //商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
        String no = UUID.randomUUID().toString();
        log.info("订单号:{}",no);
        content.put("out_trade_no", no);
        content.put("product_code", "FAST_INSTANT_TRADE_PAY");
        //订单总金额，单位为元，精确到小数点后两位
        content.put("total_amount", String.valueOf("111.99"));
        //商品标题/交易标题/订单标题/订单关键字等。 注意：不可使用特殊字符，如 /，=，&amp; 等。
        content.put("subject", "杯子");
        //商品描述，可空
        content.put("body", "好的杯子");
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        content.put("timeout_express", "5m");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayClient alipayClient = AliPayConfig.getInstance();
        request.setBizContent(JSON.toJSONString(content));
        request.setNotifyUrl(payUrlConfig.getAlipayCallbackURL());
        request.setReturnUrl(payUrlConfig.getAlipaySuccessReturnURL());
        AlipayTradePagePayResponse responseAli = alipayClient.pageExecute(request);
        if(responseAli.isSuccess()){
            System.out.println("调用成功");
            String form = responseAli.getBody();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            System.out.println("调用失败");
        }
    }
}
