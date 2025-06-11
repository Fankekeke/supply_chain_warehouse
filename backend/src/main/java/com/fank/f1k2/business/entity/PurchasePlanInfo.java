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
 * 采购计划
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchasePlanInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 采购需求单号
     */
    private String purchaseCode;

    /**
     * 物料编号
     */
    private String code;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 采购数量
     */
    private Integer purchaseNum;

    /**
     * 计划状态（0.待下发，1.计划下发，2.采购询价，3.生成订单，4.配送中，5.已完成）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 物料名称
     */
    @TableField(exist = false)
    private String materialsName;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String supplierName;


}
