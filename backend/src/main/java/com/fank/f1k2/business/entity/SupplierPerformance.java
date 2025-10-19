package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 供应商产品与业绩表
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierPerformance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 记录ID（主键）
     */
            @TableId(value = "performance_id", type = IdType.AUTO)
    private Integer performanceId;

    /**
     * 供应商ID（外键）
     */
    private Integer supplierId;

    /**
     * 记录类型（产品/业绩）
     */
    private String type;

    /**
     * 产品/服务名称 或 项目名称
     */
    private String name;

    /**
     * 销售额 (万人民币) / 对应项目金额
     */
    private BigDecimal salesAmount;

    /**
     * 占总销售额百分比
     */
    private BigDecimal salesPercentage;

    /**
     * 优势概述/产品说明
     */
    private String advantageOverview;

    /**
     * 客户名称（仅业绩字段）
     */
    private String customer;

    /**
     * 项目周期（仅业绩字段）
     */
    private String projectCycle;

    /**
     * 验收状况（仅业绩字段）
     */
    private String acceptanceStatus;

    /**
     * 备注
     */
    private String note;


}
