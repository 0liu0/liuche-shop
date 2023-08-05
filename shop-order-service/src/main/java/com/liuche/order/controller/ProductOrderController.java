package com.liuche.order.controller;

import com.liuche.common.util.JsonData;
import com.liuche.order.dto.OrderDTO;
import com.liuche.order.service.ProductOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author 刘彻
 * @Date 2023/8/3 11:51
 * @PackageName: com.liuche.order.controller
 * @ClassName: ProductOrderController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/order")
public class ProductOrderController {
    @Resource
    private ProductOrderService productOrderService;
    @PostMapping("/add")
    public JsonData addOrder(@RequestBody OrderDTO dto) {
        boolean flag = productOrderService.confirmOrder(dto);
        if (flag) { // 创建订单成功
            // 根据支付方式类型转到合适的支付
        }else { // 创建订单失败
            // 返回给前端创建失败
        }
        return JsonData.ok();
    }
    @GetMapping("/query_state/{outTradeNo}")
    public JsonData queryState(@PathVariable String outTradeNo) {
        // 根据ID查询订单状态
        String status = productOrderService.queryProductStatus(outTradeNo);
        return JsonData.ok(status);
    }
}
