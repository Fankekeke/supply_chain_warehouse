package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierEvaluate;
import com.fank.f1k2.business.service.ISupplierEvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商绩效评价 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/supplier-evaluate")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierEvaluateController {

    private final ISupplierEvaluateService supplierEvaluateService;

    /**
     * 分页获取供应商绩效评价
     *
     * @param page      分页对象
     * @param queryFrom 供应商绩效评价
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SupplierEvaluate> page, SupplierEvaluate queryFrom) {
        return R.ok(supplierEvaluateService.queryPage(page, queryFrom));
    }

    /**
     * 查询供应商绩效评价历史
     *
     * @param supplierId 供应商ID
     * @return 列表
     */
    @GetMapping("/queryEvaluateHistoryBySupplierId")
    public R queryEvaluateHistoryBySupplierId(Integer supplierId) {
        return R.ok(supplierEvaluateService.list(Wrappers.<SupplierEvaluate>lambdaQuery().eq(SupplierEvaluate::getSupplierId, supplierId)));
    }

    /**
     * 查询供应商绩效评价详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(supplierEvaluateService.getById(id));
    }

    /**
     * 查询供应商绩效评价列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(supplierEvaluateService.list());
    }

    /**
     * 新增供应商绩效评价
     *
     * @param addFrom 供应商绩效评价对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody SupplierEvaluate addFrom) throws F1k2Exception {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 判断今年是否已评价
        int year = DateUtil.year(new Date());
        int count = supplierEvaluateService.count(Wrappers.<SupplierEvaluate>lambdaQuery().eq(SupplierEvaluate::getYear, year).eq(SupplierEvaluate::getSupplierId, addFrom.getSupplierId()));
        if (count > 0) {
            throw new F1k2Exception("该供应商今年已评价");
        }
        BigDecimal totalScore = supplierEvaluateService.calculateTotalScore(addFrom);
        addFrom.setScore(totalScore);
        addFrom.setYear(String.valueOf(year));
        return R.ok(supplierEvaluateService.save(addFrom));
    }

    /**
     * 修改供应商绩效评价
     *
     * @param editFrom 供应商绩效评价对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody SupplierEvaluate editFrom) throws F1k2Exception {
        BigDecimal totalScore = supplierEvaluateService.calculateTotalScore(editFrom);
        editFrom.setScore(totalScore);
        return R.ok(supplierEvaluateService.updateById(editFrom));
    }

    /**
     * 删除供应商绩效评价
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(supplierEvaluateService.removeByIds(ids));
    }

}
