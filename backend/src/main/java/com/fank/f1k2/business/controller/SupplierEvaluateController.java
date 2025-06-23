package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierEvaluate;
import com.fank.f1k2.business.service.ISupplierEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "供应商绩效评价")
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
    @ApiOperation(value = "分页查询绩效评价", notes = "根据分页和筛选条件获取供应商绩效评价信息")
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
    @ApiOperation(value = "查询评价历史", notes = "通过供应商ID获取该供应商所有历史绩效评价记录")
    @GetMapping("/queryEvaluateHistoryBySupplierId")
    public R queryEvaluateHistoryBySupplierId(Integer supplierId) {
        return R.ok(supplierEvaluateService.list(Wrappers.<SupplierEvaluate>lambdaQuery()
                .eq(SupplierEvaluate::getSupplierId, supplierId)));
    }

    /**
     * 查询供应商绩效评价详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "绩效评价详情", notes = "通过ID获取供应商绩效评价详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(supplierEvaluateService.getById(id));
    }

    /**
     * 查询供应商绩效评价列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取绩效评价列表", notes = "列出所有供应商绩效评价记录")
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
    @ApiOperation(value = "新增绩效评价", notes = "创建一个新的供应商绩效评价记录，并校验是否已存在年度评价")
    @PostMapping
    public R save(SupplierEvaluate addFrom) throws F1k2Exception {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        int year = DateUtil.year(new Date());
        int count = supplierEvaluateService.count(Wrappers.<SupplierEvaluate>lambdaQuery()
                .eq(SupplierEvaluate::getYear, year)
                .eq(SupplierEvaluate::getSupplierId, addFrom.getSupplierId()));
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
    @ApiOperation(value = "修改绩效评价", notes = "更新已有的供应商绩效评价信息并重新计算总分")
    @PutMapping
    public R edit(SupplierEvaluate editFrom) throws F1k2Exception {
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
    @ApiOperation(value = "删除绩效评价", notes = "根据ID集合批量删除供应商绩效评价记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(supplierEvaluateService.removeByIds(ids));
    }

}
