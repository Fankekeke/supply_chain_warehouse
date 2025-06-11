package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PerformanceWeightInfo;
import com.fank.f1k2.business.dao.PerformanceWeightInfoMapper;
import com.fank.f1k2.business.service.IPerformanceWeightInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class PerformanceWeightInfoServiceImpl extends ServiceImpl<PerformanceWeightInfoMapper, PerformanceWeightInfo> implements IPerformanceWeightInfoService {

    /**
     * 分页获取绩效评价权重
     *
     * @param page      分页对象
     * @param queryFrom 绩效评价权重
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<PerformanceWeightInfo> page, PerformanceWeightInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
