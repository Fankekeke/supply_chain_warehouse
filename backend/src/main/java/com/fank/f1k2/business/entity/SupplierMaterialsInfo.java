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
 * 供应商物料
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierMaterialsInfo implements Serializable {

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
     * 批次记录
     */
    private String batchRecord;

    /**
     * 封装
     */
    private String packageSet;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 状态（0.不可供 1.可供）
     */
    private String status;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 物料名称
     */
    @TableField(exist = false)
    private String materialsName;

    /**
     * 物料名称
     */
    @TableField(exist = false)
    private String supplierName;

    /**
     * 供应商账户ID
     */
    @TableField(exist = false)
    private Integer supplierUserId;
}
