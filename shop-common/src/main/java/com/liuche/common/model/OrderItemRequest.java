package com.liuche.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 刘彻
 * @Date 2023/8/5 21:22
 * @PackageName: com.liuche.common.model
 * @ClassName: OrderItemRequest
 * @Description: 一个商品实体类标识
 */
@Data
public class OrderItemRequest implements Serializable {
    private static final long serialVersionUID = 2892917819677303457L;
    private long productId;
    private int buyNum;
}
