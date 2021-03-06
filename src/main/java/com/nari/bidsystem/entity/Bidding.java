package com.nari.bidsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.apache.ibatis.annotations.Options;

/**
 * 
 * @TableName bidding
 */
@TableName(value ="bidding")
@Data
public class Bidding implements Serializable {
    /**
     * 招标编号
     */
    @TableId(type = IdType.AUTO)
    private Integer bidId;

    /**
     * 开标时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 物料编号
     */
    private String itemNo;

    /**
     * 物料规格
     */
    private String specifications;

    /**
     * 物料重量
     */
    private Double weight;

    /**
     * 物料单位
     */
    private String unit;

    /**
     * 数量
     */
    private Double number;

    /**
     * 技术附件
     */
    private String technicalAttachments;

    /**
     * 物料技术要求描述
     */
    private String technicalRequirement;

    /**
     * 竞标单位
     */
    private String bidCompany;

    /**
     * 交货日期
     */
    private Date deliveryDate;

    /**
     * 报价次数
     */
    private Integer quoteCount;

    /**
     * 报价每轮时间（分钟）
     */
    private Integer duration;

    /**
     * 是否发布（0保存草稿，1发布）
     */
    private Integer visible;

    /**
     * 是否开始（0未开始，1进行中，2结束）
     */
    private String state;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 技术附件名称
     */
    private String technicalAttachmentsName;

    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 中标公司
     */
    private String winCompany;

    private Double winMoney;

    @TableField(exist = false)
    private Integer page;

    @TableField(exist = false)
    private Integer num;

    @TableField(exist = false)
    private String updateTimeString;

    @TableField(exist = false)
    private String startTimeString;

    @TableField(exist = false)
    private String endTimeString;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}