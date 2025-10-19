package com.fank.f1k2.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 供应商资质、专利、授权信息表
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierQualification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


            @TableId(value = "qual_id", type = IdType.AUTO)
    private Integer qualId;

    private Integer supplierId;

    /**
     * 证书名称/类型
     */
    private String certName;

    /**
     * 认证/授予机构
     */
    private String agency;

    /**
     * 适用范围
     */
    private String applyTo;

    /**
     * 签发日期
     */
    private LocalDate issueDate;

    /**
     * 有效期至
     */
    private LocalDate expiryDate;

    /**
     * 类型（认证/专利/授权代理）
     */
    private String type;

    /**
     * 特点与价值（仅专利字段）
     */
    private String featuresValue;


}
