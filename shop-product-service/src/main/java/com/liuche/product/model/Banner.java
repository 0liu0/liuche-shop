package com.liuche.product.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName banner
 */
@TableName(value ="banner")
@Data
public class Banner implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Object id;

    /**
     * 图片
     */
    @TableField(value = "img")
    private String img;

    /**
     * 跳转地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 权重
     */
    @TableField(value = "weight")
    private Integer weight;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}