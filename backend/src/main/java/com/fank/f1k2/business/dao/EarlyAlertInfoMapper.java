package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EarlyAlertInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface EarlyAlertInfoMapper extends BaseMapper<EarlyAlertInfo> {

    /**
     * 分页获取预警库存设置
     *
     * @param page       分页对象
     * @param queryFrom 预警库存设置
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<EarlyAlertInfo> page, EarlyAlertInfo queryFrom);
}
