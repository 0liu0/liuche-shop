package com.liuche.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 刘彻
 * @Date 2023/8/5 21:22
 * @PackageName: com.liuche.common.model
 * @ClassName: OrderItemDTO
 * @Description: 一个商品实体类标识
 */
@Data
public class OrderItemDTO implements Serializable {
    private static final long serialVersionUID = 2892917819677303457L;
    private long productId;
    private int buyNum;
}
