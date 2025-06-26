package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 供应商绩效评价
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 评价年份
     */
    private String year;

    /**
     * 质量评分
     */
    private BigDecimal qualityScore;

    /**
     * 成品评分
     */
    private BigDecimal costSocre;

    /**
     * 交付评分
     */
    private BigDecimal deliveryScore;

    /**
     * 服务评分
     */
    private BigDecimal serviceScore;

    /**
     * 创新与合作评分
     */
    private BigDecimal innovationScore;

    /**
     * 财务评分
     */
    private BigDecimal financialScore;

    /**
     * 综合评分
     */
    private BigDecimal score;

    /**
     * 评价时间
     */
    private String createDate;

    /**
     * 备注
     */
    private String content;

    /**
     * 评价公式
     */
    private String formula;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String supplierName;

    /**
     * 供应商账户ID
     */
    @TableField(exist = false)
    private Integer supplierUserId;

}
