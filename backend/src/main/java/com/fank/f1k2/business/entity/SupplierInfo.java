package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 供应商信息
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 供应商编号
     */
    private String code;

    /**
     * 供应商名称
     */
    private String name;

    /**
     * 备注
     */
    private String content;

    /**
     * 信用代码
     */
    private String creditCode;

    /**
     * 负责人
     */
    private String chargePerson;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 供应商图片
     */
    private String images;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 可供类型
     */
    private String supplyType;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 营业执照号
     */
    private String businessLicense;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 状态（0.未审核 1.已通过）
     */
    private String status;


}
