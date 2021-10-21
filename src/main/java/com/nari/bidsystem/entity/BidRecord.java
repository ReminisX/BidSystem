package com.nari.bidsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName bid_record
 */
@TableName(value ="bid_record")
@Data
public class BidRecord implements Serializable {
    /**
     * 投标编号
     */
    @TableId
    private String uuid;

    /**
     * 招标编号_外键
     */
    private String bidId;

    /**
     * 用户编号_外键
     */
    private String userId;

    /**
     * 报价金额
     */
    private BigDecimal quotedPrice;

    /**
     * 货期天数
     */
    private String deliveryDays;

    /**
     * 预计交货期
     */
    private Date deliveryDate;

    /**
     * 技术偏差说明
     */
    private String technicalDeviationDescription;

    /**
     * 投标第次
     */
    private Integer bidCount;

    /**
     * 价格排名
     */
    private Integer priceRanking;

    /**
     * 质量排名
     */
    private Integer qualityRanking;

    /**
     * 服务排名
     */
    private Integer serviceRanking;

    /**
     * 价格评分
     */
    private Double priceScore;

    /**
     * 质量评分
     */
    private Double qualityScore;

    /**
     * 服务评分
     */
    private Double serviceScore;

    /**
     * 总分
     */
    private Double totalScore;

    /**
     * 是否中标（0未中，1中标）
     */
    private Integer winningTheBid;

    /**
     * 是否评标（0未评，1已评）
     */
    private Integer isEvaluation;

    /**
     * 是否在招标结果中显示（0不显示，1显示）
     */
    private Integer visable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}