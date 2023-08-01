package com.liuche.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.util.CopyUtil;
import com.liuche.product.mapper.ProductMapper;
import com.liuche.product.model.Product;
import com.liuche.product.service.ProductService;
import com.liuche.product.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 70671
* @description 针对表【product】的数据库操作Service实现
* @createDate 2023-08-01 22:23:53
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService {

    /**
     * 分页查询商品信息
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> selectByPage(int page, int size) {
        Page<Product> newPage = new Page<>(page,size);
        Page<Product> productPage = this.baseMapper.selectPage(newPage, new QueryWrapper<Product>());
        HashMap<String, Object> map = new HashMap<>();
        List<Product> records = productPage.getRecords();
        List<ProductVO> list = CopyUtil.copyList(records, ProductVO.class);
        // 对商品信息进行脱敏
        map.put("total", productPage.getTotal());
        map.put("pages", productPage.getPages());
        map.put("records", list);
        return map;
    }
}




