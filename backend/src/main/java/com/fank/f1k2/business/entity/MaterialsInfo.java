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
 * 物料管理
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MaterialsInfo implements Serializable {

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
     * 描述
     */
    private String content;

    /**
     * 型号
     */
    private String model;

    /**
     * 相关图片
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
     * 物料类型
     */
    private String type;

    /**
     * 计量单位
     */
    private String measurementUnit;

    /**
     * 最小库存量
     */
    @TableField(exist = false)
    private Integer minValue;

}
