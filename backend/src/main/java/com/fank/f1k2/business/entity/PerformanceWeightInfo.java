package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 绩效评价权重
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PerformanceWeightInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 权重项
     */
    private String name;

    /**
     * 权重比率
     */
    private BigDecimal weightRate;

    /**
     * 权重项编号
     */
    private String code;

    /**
     * 子项
     */
    @TableField(exist = false)
    List<PerformanceWeightInfo> children;
}
