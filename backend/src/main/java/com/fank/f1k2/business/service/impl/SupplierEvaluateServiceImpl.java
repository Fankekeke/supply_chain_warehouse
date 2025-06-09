package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierEvaluate;
import com.fank.f1k2.business.dao.SupplierEvaluateMapper;
import com.fank.f1k2.business.service.ISupplierEvaluateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class SupplierEvaluateServiceImpl extends ServiceImpl<SupplierEvaluateMapper, SupplierEvaluate> implements ISupplierEvaluateService {

    /**
     * 分页获取供应商绩效评价
     *
     * @param page      分页对象
     * @param queryFrom 供应商绩效评价
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierEvaluate> page, SupplierEvaluate queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
