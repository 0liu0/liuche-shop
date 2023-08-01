package com.liuche.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author 刘彻
 * @Date 2023/8/1 22:37
 * @PackageName: com.liuche.product.vo
 * @ClassName: BannerVO
 * @Description: TODO
 */
@Data
public class BannerVO {
    /**
     * id
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
}
