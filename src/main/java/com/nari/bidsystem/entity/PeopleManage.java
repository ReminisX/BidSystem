package com.nari.bidsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * 
 * @TableName people_manage
 */
@TableName(value ="people_manage")
@Data
public class PeopleManage implements Serializable {
    /**
     * 
     */
    @TableId
    private String loginName;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String userGroup;

    /**
     * 
     */
    private String companyName;

    /**
     * 
     */
    private Integer companyId;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String phone;

    private String password;

    private Integer identity;

    @TableField(exist = false)
    private Integer page;

    @TableField(exist = false)
    private Integer num;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}