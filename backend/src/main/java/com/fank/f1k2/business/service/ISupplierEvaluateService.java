package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierEvaluate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.common.exception.F1k2Exception;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface ISupplierEvaluateService extends IService<SupplierEvaluate> {

    /**
     * 分页获取供应商绩效评价
     *
     * @param page      分页对象
     * @param queryFrom 供应商绩效评价
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierEvaluate> page, SupplierEvaluate queryFrom);

    /**
     * 计算供应商绩效评价总分
     *
     * @param supplierEvaluate 供应商绩效评价
     * @return 结果
     */
    BigDecimal calculateTotalScore(SupplierEvaluate supplierEvaluate) throws F1k2Exception;
}
