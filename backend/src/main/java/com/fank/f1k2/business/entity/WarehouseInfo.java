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
 * 库房库存
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehouseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 物料编号
     */
    private String code;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 交易类型（0.库房存货 1.入库信息 2.出库信息）
     */
    private Integer transactionType;

    /**
     * 操作时间
     */
    private String createDate;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 入库单号
     */
    private String inboundOrderNumber;

    /**
     * 出库单号
     */
    private String deliveryOrderNumber;

    /**
     * 物料名称
     */
    @TableField(exist = false)
    private String materialsName;


}
