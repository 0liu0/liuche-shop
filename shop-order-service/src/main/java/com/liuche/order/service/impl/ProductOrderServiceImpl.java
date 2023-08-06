package com.liuche.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.enums.CouponStateEnum;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.JsonData;
import com.liuche.order.dto.OrderDTO;
import com.liuche.order.feign.AddressFeign;
import com.liuche.order.feign.CouponFeign;
import com.liuche.order.mapper.ProductOrderMapper;
import com.liuche.order.model.ProductOrder;
import com.liuche.order.service.ProductOrderService;
import com.liuche.order.vo.AddressInfoResp;
import com.liuche.order.vo.CouponRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 70671
 * @description 针对表【product_order】的数据库操作Service实现
 * @createDate 2023-08-03 11:43:32
 */
@Service
@Slf4j
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder>
        implements ProductOrderService {
    @Resource
    private AddressFeign addressFeign;
    @Resource
    private CouponFeign couponFeign;

    /**
     * service编写伪代码
     * 防重提交
     * 用户微服务-确认收货地址
     * 商品微服务-获取最新购物项和价格
     * 订单验价
     * 优惠券微服务-获取优惠券
     * 验证价格
     * 锁定优惠券
     * 锁定商品库存
     * 创建订单对象
     * 创建子订单对象
     * 发送延迟消息-用于自动关单
     * 创建支付信息-对接三方支付
     *
     * @param dto
     * @return
     */
    public boolean confirmOrder(OrderDTO dto) {
        log.info("前端传来的信息：" + dto);
        // 得到用户收货地址地址
        JsonData address = addressFeign.getOneAddress(dto.getAddressId());
        // 得到用户的收货地址
        AddressInfoResp addressInfo = address.getData(new TypeReference<>() {
        });
        // 得到对应的优惠券
        ArrayList<CouponRecordVO> recordList = new ArrayList<>();
        for (Long id : dto.getCouponRecordIds()) {
            JsonData jsonData = couponFeign.findUserCouponRecordById(id);
            CouponRecordVO recordVO = jsonData.getData(new TypeReference<>() {
            });
            recordList.add(recordVO);
        }
        this.checkRecord(recordList);
        return true;
    }

    private void checkRecord(List<CouponRecordVO> recordList) {
        // 校验
        for (CouponRecordVO recordVO : recordList) {
            if (!Objects.equals(recordVO.getUseState(), CouponStateEnum.NEW.name())) {
                throw new BusinessException(ExceptionCode.PARAMS_ERROR, "优惠券不可用");
            }
            long startTime = recordVO.getStartTime().getTime();
            long endTime = recordVO.getEndTime().getTime();
            long now = new Date().getTime();
            if (now > endTime || now < startTime) {
                throw new BusinessException(ExceptionCode.COUPON_OUT_OF_TIME);
            }
        }
    }

    @Override
    public String queryProductStatus(String outTradeNo) {
        return this.baseMapper.queryProductStatus(outTradeNo);
    }
}




