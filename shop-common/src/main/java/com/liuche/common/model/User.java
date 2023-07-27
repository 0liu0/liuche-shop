package com.liuche.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/7/27 16:55
 * @PackageName: com.liuche.common.model
 * @ClassName: User
 * @Description: TODO
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 3290018139830806045L;
    /**
     *
     */
    private Long id;

    /**
     * 昵称
     */
    private String name;
    /**
     * 邮箱
     */
    private String mail;
}
