package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PerformanceWeightInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface PerformanceWeightInfoMapper extends BaseMapper<PerformanceWeightInfo> {

    /**
     * 分页获取绩效评价权重
     *
     * @param page       分页对象
     * @param queryFrom 绩效评价权重
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PerformanceWeightInfo> page, @Param("queryForm") PerformanceWeightInfo queryFrom);
}
