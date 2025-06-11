package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PerformanceWeightInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IPerformanceWeightInfoService extends IService<PerformanceWeightInfo> {

    /**
     * 分页获取绩效评价权重
     *
     * @param page      分页对象
     * @param queryFrom 绩效评价权重
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PerformanceWeightInfo> page, PerformanceWeightInfo queryFrom);
}
