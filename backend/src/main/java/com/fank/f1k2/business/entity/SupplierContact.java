package com.fank.f1k2.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 供应商联系人信息表
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierContact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 联系人ID（主键）
     */
    @TableId(value = "contact_id", type = IdType.AUTO)
    private Integer contactId;

    /**
     * 供应商ID（外键）
     */
    private Integer supplierId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 职务/职别
     */
    private String title;

    /**
     * 部门
     */
    private String department;

    /**
     * 联系方式
     */
    private String contactNum;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 联系人类型（商务/技术）
     */
    private String contactType;


}
