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
 * 消息通知
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NotifyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 消息内容
     */
    private String content;

    /**
     * 状态（0.未读 1.已读）
     */
    private String status;

    /**
     * 所属人
     */
    private Integer userId;

    /**
     * 类型（1.供应商 2.员工）
     */
    private String agencyType;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 已读时间
     */
    private String finishDate;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String supplierName;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String staffName;
}
