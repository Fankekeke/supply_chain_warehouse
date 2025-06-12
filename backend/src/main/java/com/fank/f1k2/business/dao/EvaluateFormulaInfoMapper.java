package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EvaluateFormulaInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface EvaluateFormulaInfoMapper extends BaseMapper<EvaluateFormulaInfo> {

    /**
     * 分页获取计算公式
     *
     * @param page      分页对象
     * @param queryFrom 计算公式
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<EvaluateFormulaInfo> page, EvaluateFormulaInfo queryFrom);
}
