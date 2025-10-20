package com.fank.f1k2.business.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 供应商基本信息主表
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierMain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 供应商ID（主键）
     */
    @TableId(value = "supplier_id", type = IdType.AUTO)
    private Integer supplierId;

    /**
     * 企业名称
     */
    private String corpName;

    /**
     * 公司法人
     */
    private String legalPerson;

    /**
     * 联系方式（公司电话）
     */
    private String contactInfo;

    /**
     * 注册资本 (万元)
     */
    private BigDecimal regCapital;

    /**
     * 营业执照号码
     */
    private String licenseNum;

    /**
     * 行业类别
     */
    private String industryType;

    /**
     * 业务性质 (生产企业/销售公司/服务机构等)
     */
    private String businessNature;

    /**
     * 资本类型 (有限责任公司/股份公司等)
     */
    private String capitalType;

    /**
     * 股权结构情况
     */
    private String equityStructure;

    /**
     * 主要经营范围
     */
    private String businessScope;

    /**
     * 是否有进出口业务 (0:无, 1:有)
     */
    private Boolean hasImportExport;

    /**
     * 进出口模式 (自营出口/代理出口)
     */
    private String ieMode;

    /**
     * 企业注册地址
     */
    private String regAddress;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 企业网址
     */
    private String website;

    /**
     * 企业成立时间
     */
    private String establishDate;

    /**
     * 企业规模
     */
    private String scale;

    /**
     * 参保人数
     */
    private Integer insuredNum;

    /**
     * 年产值 (万人民币)
     */
    private BigDecimal annualOutput;

    /**
     * 年销售额 (万人民币)
     */
    private BigDecimal annualSales;

    /**
     * 公司简介 (含发展历程、业务、合规情况)
     */
    private String corpProfile;

    /**
     * 基本证书（营业执照等勾选情况）
     */
    private String requiredCerts;

    /**
     * 创建时间
     */
    private String createdAt;


}
