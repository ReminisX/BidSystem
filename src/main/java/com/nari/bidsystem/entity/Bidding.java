package com.nari.bidsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName bidding
 */
@TableName(value ="bidding")
@Data
public class Bidding implements Serializable {
    /**
     * 
     */
    @TableId
    private String bidId;

    /**
     * 
     */
    private Date startTime;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private String itemNo;

    /**
     * 
     */
    private String specifications;

    /**
     * 
     */
    private Double weight;

    /**
     * 
     */
    private String unit;

    /**
     * 
     */
    private Double number;

    /**
     * 
     */
    private String technicalAttachments;

    /**
     * 
     */
    private String technicalRequirement;

    /**
     * 
     */
    private String bidCompany;

    /**
     * 
     */
    private Date deliveryDate;

    /**
     * 
     */
    private Integer quoteCount;

    /**
     * 
     */
    private Integer duration;

    /**
     * 
     */
    private Integer visible;

    /**
     * 
     */
    private Integer state;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String technicalAttachmentsName;

    /**
     * 
     */
    private String itemName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}