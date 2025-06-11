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
 * 采购订单
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 采购订单编号
     */
    private String code;

    /**
     * 采购计划ID
     */
    private Integer planId;

    /**
     * 物料编号
     */
    private String materialsCode;

    /**
     * 报价金额
     */
    private BigDecimal totalPrice;

    /**
     * 所属供应商
     */
    private Integer supplierId;

    /**
     * 订单状态（0.未付款 1.已付款 2.已发货 3.检验中 4.已退货 5.已入库）
     */
    private String status;

    /**
     * 监察人
     */
    private String auditUser;

    /**
     * 合格率
     */
    private BigDecimal passRate;

    /**
     * 驳回内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private String createDate;

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
}
