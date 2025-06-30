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
 * 供应商审核记录
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierAuditRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 审核编号
     */
    private String code;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 审核备注
     */
    private String content;

    /**
     * 审核状态（0.未审核 1.通过 2.驳回）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 审核时间
     */
    private String auditDate;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String supplierName;
    private String auditContent;


}
