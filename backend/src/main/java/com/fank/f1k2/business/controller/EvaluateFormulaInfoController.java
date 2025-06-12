package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EvaluateFormulaInfo;
import com.fank.f1k2.business.service.IEvaluateFormulaInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 计算公式 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/evaluate-formula-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluateFormulaInfoController {

    private final IEvaluateFormulaInfoService evaluateFormulaInfoService;

    /**
     * 分页获取计算公式
     *
     * @param page      分页对象
     * @param queryFrom 计算公式
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<EvaluateFormulaInfo> page, EvaluateFormulaInfo queryFrom) {
        return R.ok(evaluateFormulaInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询计算公式详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(evaluateFormulaInfoService.getById(id));
    }

    /**
     * 查询计算公式列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(evaluateFormulaInfoService.list());
    }

    /**
     * 新增计算公式
     *
     * @param addFrom 计算公式对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody EvaluateFormulaInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        if ("1".equals(addFrom.getUseFlag())) {
            // 更新其他状态为0
            evaluateFormulaInfoService.update(Wrappers.<EvaluateFormulaInfo>lambdaUpdate().set(EvaluateFormulaInfo::getUseFlag, "0"));
        }
        return R.ok(evaluateFormulaInfoService.save(addFrom));
    }

    /**
     * 修改计算公式
     *
     * @param editFrom 计算公式对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody EvaluateFormulaInfo editFrom) {
        if ("1".equals(editFrom.getUseFlag())) {
            // 更新其他状态为0
            evaluateFormulaInfoService.update(Wrappers.<EvaluateFormulaInfo>lambdaUpdate().set(EvaluateFormulaInfo::getUseFlag, "0"));
        }
        return R.ok(evaluateFormulaInfoService.updateById(editFrom));
    }

    /**
     * 删除计算公式
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(evaluateFormulaInfoService.removeByIds(ids));
    }

}
