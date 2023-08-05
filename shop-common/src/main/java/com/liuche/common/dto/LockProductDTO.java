package com.liuche.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author 刘彻
 * @Date 2023/8/5 21:21
 * @PackageName: com.liuche.common.model
 * @ClassName: LockProductDTO
 * @Description: 锁住库存的实体类
 */
@Data
public class LockProductDTO implements Serializable {
    private static final long serialVersionUID = -6470336797989520987L;
    private String orderOutTradeNo;
    private List<OrderItemDTO> orderItemList;
}
