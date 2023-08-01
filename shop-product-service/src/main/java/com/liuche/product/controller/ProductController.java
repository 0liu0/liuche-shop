package com.liuche.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.product.model.Banner;
import com.liuche.product.service.ProductService;
import com.liuche.product.vo.BannerVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
