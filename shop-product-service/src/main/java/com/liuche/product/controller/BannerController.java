package com.liuche.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.product.model.Banner;
import com.liuche.product.service.BannerService;
import com.liuche.product.vo.BannerVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 刘彻
 * @Date 2023/8/1 22:33
 * @PackageName: com.liuche.product.controller
 * @ClassName: BannerController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {
    @Resource
    private BannerService bannerService;
    @ApiOperation("轮播图列表接口")
    @GetMapping("/list")
    public JsonData getList() {
        List<Banner> banners = bannerService.list(new QueryWrapper<Banner>().orderByAsc("weight"));
        List<BannerVO> res = CopyUtil.copyList(banners, BannerVO.class);
        return JsonData.ok(res);
    }
}
