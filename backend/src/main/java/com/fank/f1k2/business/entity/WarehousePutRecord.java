package com.fank.f1k2.business.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 库存入库记录
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehousePutRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 入库编号
     */
    private String code;

    /**
     * 入库单名称
     */
    private String name;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 入库人
     */
    private String putUser;

    /**
     * 入库单备注
     */
    private String remark;

    /**
     * 入库时间
     */
    private String createDate;

    /**
     * 删除标识
     */
    private String delFlag;


}
