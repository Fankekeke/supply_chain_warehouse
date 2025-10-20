package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.Year;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 供应商财务信息表
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierFinance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 供应商ID（外键）
     */
    private Integer supplierId;

    /**
     * 年份
     */
    private Year year;

    /**
     * 收入总额 (万人民币)
     */
    private BigDecimal totalRevenue;

    /**
     * 净利润总额 (万人民币)
     */
    private BigDecimal netProfit;

    /**
     * 其他说明企业实力的财务数据
     */
    private String otherData;


}
