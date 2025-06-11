package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.NotifyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface NotifyInfoMapper extends BaseMapper<NotifyInfo> {

    /**
     * 分页获取消息通知-供应商
     *
     * @param page       分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageBySupplier(Page<NotifyInfo> page, @Param("queryForm") NotifyInfo queryFrom);

    /**
     * 分页获取消息通知-员工
     *
     * @param page       分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageByStaff(Page<NotifyInfo> page, @Param("queryForm") NotifyInfo queryFrom);
}
