package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.LogisticsInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface ILogisticsInfoService extends IService<LogisticsInfo> {

    /**
     * 分页获取采购订单物流
     *
     * @param page       分页对象
     * @param queryFrom 采购订单物流
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<LogisticsInfo> page, LogisticsInfo queryFrom);
}
