package com.liuche.order.controller;

import com.liuche.common.util.JsonData;
import com.liuche.order.service.ProductOrderService;
import com.liuche.order.vo.OrderQueryVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author 刘彻
 * @Date 2023/8/8 18:45
 * @PackageName: com.liuche.order.controller
 * @ClassName: OrderController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {
    @Resource
    private ProductOrderService productOrderService;
    @PostMapping("/callback/alipay")
    @ApiOperation("订单支付成功回调接口")
    public void alipayReturn(HttpServletRequest request, HttpServletResponse response) {
        log.info("我运行了");
        Map<String, String> stringStringMap = convertRequestParamsToMap(request);
        String out_trade_no = stringStringMap.get("out_trade_no");
        // 将订单id改为已支付
        productOrderService.updateOrderByTradeNo(out_trade_no);
        log.info("订单状态已修改为已支付");
    }

    @GetMapping("/list/{type}/{page}/{size}")
    @ApiOperation("查询用户订单详情")
    public JsonData queryOrderList(@PathVariable Integer page, @PathVariable Integer size,@ApiParam(example = "PAY") @PathVariable String type){
        HashMap<String, Object> map = productOrderService.queryByPage(page,size,type);
        log.info("得到用户购物车实例");
        return JsonData.ok(map);
    }

    /**
     * 将request中的参数转换成Map
     * @param request
     * @return
     */
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<>(16);
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int size = values.length;
            if (size == 1) {
                paramsMap.put(name, values[0]);
            } else {
                paramsMap.put(name, "");
            }
        }
        return paramsMap;
    }
}
