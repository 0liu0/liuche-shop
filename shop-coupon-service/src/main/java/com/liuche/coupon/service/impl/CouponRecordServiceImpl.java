package com.liuche.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.liuche.common.dto.LockCouponRecordDTO;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.enums.ProductOrderStateEnum;
import com.liuche.common.enums.StockTaskStateEnum;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.model.CouponRecordMessage;
import com.liuche.common.util.JsonData;
import com.liuche.common.util.RequestContext;
import com.liuche.coupon.config.MQConfig;
import com.liuche.coupon.feign.OrderFeign;
import com.liuche.coupon.mapper.CouponRecordMapper;
import com.liuche.coupon.mapper.CouponTaskMapper;
import com.liuche.coupon.model.CouponRecord;
import com.liuche.coupon.model.CouponTask;
import com.liuche.coupon.service.CouponRecordService;
import com.liuche.coupon.service.CouponTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 70671
 * @description 针对表【coupon_record】的数据库操作Service实现
 * @createDate 2023-07-28 20:39:26
 */
@Service
@Slf4j
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecord>
        implements CouponRecordService {

    @Resource
    private CouponTaskService couponTaskService;
    @Resource
    private CouponTaskMapper couponTaskMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MQConfig mqConfig;
    @Resource
    private OrderFeign orderFeign;
    @Resource
    private CouponRecordMapper couponRecordMapper;

    /**
     * 分页查询我的优惠券列表
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> getPage(int page, int size) {
        // 得到用户id
        long userId = RequestContext.getUserId();
        // 根据用胡id查询用户自己所有的优惠券
        Page<CouponRecord> pageInfo = new Page<>(page, size);
        QueryWrapper<CouponRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        Page<CouponRecord> couponRecordPage = this.baseMapper.selectPage(pageInfo, queryWrapper);
        Map<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", couponRecordPage.getTotal());
        pageMap.put("total_page", couponRecordPage.getPages());
        pageMap.put("current_data", couponRecordPage.getRecords());
        return pageMap;
    }

    @Override
    @Transactional
    public JsonData lockCouponRecords(LockCouponRecordDTO dto) {
        // 得到优惠券记录id以及订单编号和用户id
        List<Long> recordIdList = dto.getLockCouponRecordIds();
        String outTradeNo = dto.getOrderOutTradeNo();
        Long userId = dto.getUserId();
        // 修改用户优惠券的状态为使用过的USED
        int num = this.baseMapper.updateUseStateByIdBatch(recordIdList, userId,outTradeNo);
        // 将这些锁定记录记录在coupon_task表中
        List<CouponTask> tasks = recordIdList.stream().map(obj -> {
            CouponTask couponTask = new CouponTask();
            couponTask.setCouponRecordId(obj);
            couponTask.setLockState("LOCK");
            couponTask.setOutTradeNo(outTradeNo);
            return couponTask;
        }).collect(Collectors.toList());
        couponTaskService.saveBatch(tasks);
        // 检测修改的值和用户使用优惠券的值一不一样，不一样则抛异常
        if (num != recordIdList.size()) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "优惠券使用有问题哦");
        }
        // 发送延迟队列到mq
        for (CouponTask task : tasks) {
            CouponRecordMessage message = new CouponRecordMessage();
            message.setOutTradeNo(outTradeNo);
            message.setTaskId(task.getId());
            rabbitTemplate.convertAndSend(mqConfig.getEventExchange(), mqConfig.getCouponReleaseDelayRoutingKey(), message);
            log.info("优惠券锁定消息发送成功！{}", message);
        }
        return JsonData.ok(true);
    }

    @Override
    @Transactional
    public boolean checkOrderOfCoupon(CouponRecordMessage msg) {
        // 查询couponTask是否存在
        CouponTask couponTask = couponTaskService.getOne(new QueryWrapper<CouponTask>().eq("id", msg.getTaskId()));
        if (couponTask == null) {
            log.warn("工作单不存在，消息：{}", msg);
            return true;
        }
        // 当当前task状态为LOCK才走流程
        if (couponTask.getLockState().equals(StockTaskStateEnum.LOCK.name())) {
            // 调用远程接口，查看订单状态
            JsonData jsonData = orderFeign.queryState(msg.getOutTradeNo());
            if (jsonData.getCode() == 0) { // 响应正常
                // 得到响应的状态信息
                String status = jsonData.getData().toString();
                if (status.equals(ProductOrderStateEnum.PAY.name())) {
                    // 如果订单已经支付了，更新coupon_task表为FINISH
                    Long taskId = msg.getTaskId();
                    int num = couponTaskMapper.confirmRecord(taskId);
                    log.info("订单已完成：{}", msg);
                    return num > 0;
                } else if (status.equals(ProductOrderStateEnum.NEW.name())) {
                    // 订单状态为还未消费
                    log.info("还未消费哦");
                    return false;
                }
            }
            // 修改couponTask为CANCEL
            couponTaskMapper.updateRecordTaskCancel(msg.getTaskId());
            couponRecordMapper.releaseCoupon(couponTask.getCouponRecordId());
            log.info("订单不存在或已取消：{}", msg);
        } else {
            log.warn("工作单状态不是LOCK，state={}，消息体={}",couponTask.getLockState(),msg);
        }
        return true;
    }
}




