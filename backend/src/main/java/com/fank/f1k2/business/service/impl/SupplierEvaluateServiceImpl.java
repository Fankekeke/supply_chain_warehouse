package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EvaluateFormulaInfo;
import com.fank.f1k2.business.entity.PerformanceWeightInfo;
import com.fank.f1k2.business.entity.SupplierEvaluate;
import com.fank.f1k2.business.dao.SupplierEvaluateMapper;
import com.fank.f1k2.business.service.IEvaluateFormulaInfoService;
import com.fank.f1k2.business.service.IPerformanceWeightInfoService;
import com.fank.f1k2.business.service.ISupplierEvaluateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierEvaluateServiceImpl extends ServiceImpl<SupplierEvaluateMapper, SupplierEvaluate> implements ISupplierEvaluateService {

    private final IEvaluateFormulaInfoService evaluateFormulaInfoService;

    private final IPerformanceWeightInfoService performanceWeightInfoService;

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

    /**
     * 计算供应商绩效评价总分
     *
     * @param supplierEvaluate 供应商绩效评价
     * @return 结果
     */
    @Override
    public BigDecimal calculateTotalScore(SupplierEvaluate supplierEvaluate) throws F1k2Exception {
        // 获取默认计算公式
        EvaluateFormulaInfo evaluateFormulaInfo = evaluateFormulaInfoService.getOne(Wrappers.<EvaluateFormulaInfo>lambdaQuery().eq(EvaluateFormulaInfo::getUseFlag, "1"));
        if (evaluateFormulaInfo == null) {
            throw new F1k2Exception("请先设置默认计算公式");
        }
        // 获取权重比率
        List<PerformanceWeightInfo> performanceWeightInfoList = performanceWeightInfoService.list(Wrappers.<PerformanceWeightInfo>lambdaQuery());
        if (CollectionUtil.isEmpty(performanceWeightInfoList)) {
            throw new F1k2Exception("请先设置权重比率");
        }
        Map<String, BigDecimal> weightMap = performanceWeightInfoList.stream().collect(Collectors.toMap(PerformanceWeightInfo::getCode, PerformanceWeightInfo::getWeightRate));

        String expression = "(质量评分*质量权重) + (成品评分+成品权重) + (交付评分*交付权重) + (服务评分*服务权重) + (创新与合作评分*创新与合作权重) + (财务评分*财务权重)";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<String, Object>(16);
        env.put("质量评分", supplierEvaluate.getQualityScore());
        env.put("成品评分", supplierEvaluate.getCostSocre());
        env.put("交付评分", supplierEvaluate.getDeliveryScore());
        env.put("服务评分", supplierEvaluate.getServiceScore());
        env.put("创新与合作评分", supplierEvaluate.getInnovationScore());
        env.put("财务评分", supplierEvaluate.getFinancialScore());
        weightMap.forEach((key, value) -> {
            env.put(key + "权重", value);
        });
        // 执行表达式
        return (BigDecimal) compiledExp.execute(env);
    }
}
