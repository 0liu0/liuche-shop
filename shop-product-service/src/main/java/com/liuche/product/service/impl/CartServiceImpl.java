package com.liuche.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuche.common.constants.RedisConstant;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.RequestContext;
import com.liuche.product.dto.AddProductDTO;
import com.liuche.product.dto.UpdateProductDTO;
import com.liuche.product.model.Product;
import com.liuche.product.service.CartService;
import com.liuche.product.service.ProductService;
import com.liuche.product.vo.CartItemVO;
import com.liuche.product.vo.CartVO;
import com.liuche.product.vo.ProductVO;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author 刘彻
 * @Date 2023/8/2 14:38
 * @PackageName: com.liuche.product.service.impl
 * @ClassName: CartServiceImpl
 * @Description: 购物车业务层
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Resource
    private ProductService productService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean addProduct(AddProductDTO dto) {
        Long productId = dto.getProductId();
        int buyNum = dto.getBuyNum();
        // 得到用户的购物车
        // 无论userCartOps有没有值都不会报错
        BoundHashOperations<String, Object, Object> userCartOps = this.getUserCartOps();
        Object cacheObj = userCartOps.get(String.valueOf(productId));
        if (cacheObj == null) { // 之前未添加该产品
            // 从数据库中搜索该产品
            Product product = productService.getById(productId);
            if (product == null) throw new BusinessException(ExceptionCode.PARAMS_ERROR, "该产品不存在");
            // 将信息进行脱敏加入到购物车
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setProductId(productId);
            cartItemVO.setAmount(product.getPrice());
            cartItemVO.setBuyNum(buyNum);
            cartItemVO.setProductImg(product.getCoverImg());
            cartItemVO.setProductTitle(product.getTitle());
            userCartOps.put(String.valueOf(productId), cartItemVO);
        } else { // 之前添加过该产品
            CartItemVO itemVO = (CartItemVO) cacheObj;
            itemVO.setBuyNum(itemVO.getBuyNum() + buyNum);
            userCartOps.put(String.valueOf(productId), itemVO);
        }
        return true;
    }

    @Override
    public boolean clearCart() {
        redisTemplate.delete(getCartKey());
        return true;
    }

    @Override
    public CartVO getUserCartInfo() {
        CartVO res = new CartVO();
        // 得到用户的购物车
        BoundHashOperations<String, Object, Object> userCartOps = getUserCartOps();
        // 更新用户购物车里商品的信息
        Set<Object> keys = userCartOps.keys(); // 得到用户所有的商品信息
        if (keys != null && keys.size() > 0) {
            // 从数据库中得到最新所有的商品信息
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", keys);
            List<Product> productList = productService.list(queryWrapper);
            // 用来存储更新后的CartItem
            List<CartItemVO> list = new LinkedList<>();
            // 遍历购物车的商品
            for (Product product : productList) {
                // 更新数据
                CartItemVO item = (CartItemVO) userCartOps.get(String.valueOf(product.getId()));
                item.setAmount(product.getPrice());
                item.setProductTitle(product.getTitle());
                item.setProductImg(product.getCoverImg());
                list.add(item);
            }
            // 得到返回结果
            res.setCartItems(list);
            return res;
        }
        return null;
    }

    @Override
    public boolean delProduct(String id) {
        // 得到用户的购物车
        BoundHashOperations<String, Object, Object> userCartOps = getUserCartOps();
        Long delete = userCartOps.delete(id);
        log.info("delete->" + delete);
        return delete != 0;
    }

    @Override
    public boolean updateCartProductInfo(UpdateProductDTO productDTO) {
        // 得到用户的购物车
        BoundHashOperations<String, Object, Object> userCartOps = getUserCartOps();
        CartItemVO itemVO = (CartItemVO) userCartOps.get(String.valueOf(productDTO.getProductId()));
        if (itemVO == null) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "您的购物车没有此商品");
        }
        itemVO.setBuyNum(productDTO.getBuyNum());
        // 保存至redis
        userCartOps.put(String.valueOf(productDTO.getProductId()), itemVO);
        return true;
    }

    @Override
    public CartVO getUserCartProductInfo(List<Long> prductIdList) {
        CartVO res = new CartVO();
        // 得到用户购物车的所有信息
        BoundHashOperations<String, Object, Object> userCartOps = this.getUserCartOps();
        // 得到新的商品信息
        List<Product> products = productService.listByIds(prductIdList);
        List<CartItemVO> list = products.stream().map(item -> {
            // 查看库存数量是否符合 （写在这儿确实不太妥当，不过先这样吧）
            CartItemVO itemVO = (CartItemVO) userCartOps.get(String.valueOf(item.getId()));
            if (item.getStock() - item.getLockStock() < itemVO.getBuyNum()) {
                throw new BusinessException(ExceptionCode.PARAMS_ERROR, "库存不足");
            }
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setProductId(item.getId());
            cartItemVO.setAmount(item.getPrice());
            cartItemVO.setProductTitle(item.getTitle());
            cartItemVO.setProductImg(item.getCoverImg());
            cartItemVO.setBuyNum(itemVO.getBuyNum());
            return cartItemVO;
        }).collect(Collectors.toList());
        res.setCartItems(list);
        return res;
    }

    @Override
    public boolean reduceCartOps(List<Long> idList) {
        // 得到用户购物车的所有信息
        try {
            BoundHashOperations<String, Object, Object> userCartOps = this.getUserCartOps();
            for (Long id : idList) {
                userCartOps.delete(String.valueOf(id));
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 得到购物车的key值
     *
     * @return
     */
    private String getCartKey() {
        long userId = RequestContext.getUserId();
        return RedisConstant.USER_CART + userId;
    }

    /**
     * 得到用户的购物车
     */
    private BoundHashOperations<String, Object, Object> getUserCartOps() {
        // 得到用户的购物车id
        String cartKey = getCartKey();
        // 从redis拿到用户的购物车
        return redisTemplate.boundHashOps(cartKey);
    }

}
