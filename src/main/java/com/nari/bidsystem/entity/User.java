package com.nari.bidsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 登录名
     */
    @TableId
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 登录身份，0内部，1供应商
     */
    private int identity;

    @TableField(exist = false)
    private Integer page;

    @TableField(exist = false)
    private Integer num;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}