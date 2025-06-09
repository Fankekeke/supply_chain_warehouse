package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierEvaluate;
import com.fank.f1k2.business.service.ISupplierEvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    private final ISupplierEvaluateService bulletinInfoService;

    /**
     * 分页获取供应商绩效评价
     *
     * @param page      分页对象
     * @param queryFrom 供应商绩效评价
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SupplierEvaluate> page, SupplierEvaluate queryFrom) {
        return R.ok(bulletinInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询供应商绩效评价详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    /**
     * 查询供应商绩效评价列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
     * 新增供应商绩效评价
     *
     * @param addFrom 供应商绩效评价对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody SupplierEvaluate addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(addFrom));
    }

    /**
     * 修改供应商绩效评价
     *
     * @param editFrom 供应商绩效评价对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody SupplierEvaluate editFrom) {
        return R.ok(bulletinInfoService.updateById(editFrom));
    }

    /**
     * 删除供应商绩效评价
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }

}
