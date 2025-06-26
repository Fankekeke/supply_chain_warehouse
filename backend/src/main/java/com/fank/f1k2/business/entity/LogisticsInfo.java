package com.fank.f1k2.business.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 采购订单物流
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LogisticsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 物流备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 是否为当前物流（0.否 1.是）
     */
    private Integer currentLogistics;

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
     * 订单编号
     */
    @TableField(exist = false)
    private String orderCode;

    /**
     * 采购物料名称
     */
    @TableField(exist = false)
    private String materialsName;

    /**
     * 供应商账户ID
     */
    @TableField(exist = false)
    private Integer supplierUserId;
}
