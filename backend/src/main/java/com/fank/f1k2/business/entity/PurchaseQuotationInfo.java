package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 采购计划报价管理
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseQuotationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 采购计划ID
     */
    private Integer planId;

    /**
     * 所属供应商
     */
    private Integer supplierId;

    /**
     * 状态（0.未接收 1.已接收 2.已报价）
     */
    private String status;

    /**
     * 物料编号
     */
    private String materialsCode;

    /**
     * 报价金额
     */
    private BigDecimal totalPrice;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 报价时间
     */
    private String quotationDate;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 报价截至时间
     */
    private String endDate;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 聊天内容
     */
    private String chatContent;

    /**
     * 采购计划编号
     */
    @TableField(exist = false)
    private String planCode;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String supplierName;

    /**
     * 物料名称
     */
    @TableField(exist = false)
    private String materialsName;

    /**
     * 采购计划报价管理
     */
    @TableField(exist = false)
    private String purchaseQuotationInfoList;

    /**
     * 供应商账户ID
     */
    @TableField(exist = false)
    private Integer supplierUserId;

    @TableField(exist = false)
    private Integer num;
}
