package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EvaluateFormulaInfo;
import com.fank.f1k2.business.dao.EvaluateFormulaInfoMapper;
import com.fank.f1k2.business.service.IEvaluateFormulaInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class EvaluateFormulaInfoServiceImpl extends ServiceImpl<EvaluateFormulaInfoMapper, EvaluateFormulaInfo> implements IEvaluateFormulaInfoService {

    /**
     * 分页获取计算公式
     *
     * @param page      分页对象
     * @param queryFrom 计算公式
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<EvaluateFormulaInfo> page, EvaluateFormulaInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
