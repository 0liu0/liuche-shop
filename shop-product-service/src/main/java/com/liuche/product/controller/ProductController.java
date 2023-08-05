package com.liuche.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuche.common.dto.LockCouponRecordDTO;
import com.liuche.common.dto.LockProductDTO;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.product.model.Banner;
import com.liuche.product.service.ProductService;
import com.liuche.product.vo.BannerVO;
import com.liuche.product.vo.ProductVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author 刘彻
 * @Date 2023/8/1 22:43
 * @PackageName: com.liuche.product.controller
 * @ClassName: ProductController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @ApiOperation("商品列表分页接口开发")
    @GetMapping("/list/{page}/{size}")
    public JsonData getList(@PathVariable int page, @PathVariable int size) {
        Map<String, Object> map = productService.selectByPage(page, size);
        return JsonData.ok(map);
    }

    @ApiOperation("商品详情页接口开发")
    @GetMapping("/detail/{productId}")
    public JsonData getDetail(@PathVariable long productId) {
        ProductVO productVO = CopyUtil.copy(productService.getById(productId), ProductVO.class);
        return JsonData.ok(productVO);
    }
    @ApiOperation("RPC-订单服务调用锁定优惠券记录")
    @PostMapping("/lock_stock")
    public JsonData lockCouponRecords(@ApiParam("锁定优惠券") @RequestBody LockProductDTO dto) {
        return productService.lockProductStock(dto);
    }
}
