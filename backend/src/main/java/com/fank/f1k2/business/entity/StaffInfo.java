package com.fank.f1k2.business.entity;

import java.time.LocalDate;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 员工管理
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StaffInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 员工编号
     */
    private String code;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 性别（1.男 2.女）
     */
    private Integer staffSex;

    /**
     * 员工头像
     */
    private String images;

    /**
     * 入职时间
     */
    private String createDate;

    /**
     * 出生日期
     */
    private String birthDate;

    /**
     * 备注
     */
    private String content;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 账户ID
     */
    private Integer sysUserId;

    /**
     * 所属部门
     */
    private Integer deptId;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;


}
