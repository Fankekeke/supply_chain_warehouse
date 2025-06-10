package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PerformanceWeightInfo;
import com.fank.f1k2.business.service.IPerformanceWeightInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 绩效评价权重 控制层
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/performance-weight-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PerformanceWeightInfoController {

    private final IPerformanceWeightInfoService performanceWeightInfoService;

    /**
    * 分页获取绩效评价权重
    *
    * @param page       分页对象
    * @param queryFrom 绩效评价权重
    * @return 结果
    */
    @GetMapping("/page")
    public R page(Page<PerformanceWeightInfo> page, PerformanceWeightInfo queryFrom) {
        return R.ok(performanceWeightInfoService.queryPage(page, queryFrom));
    }

    /**
    * 查询绩效评价权重详情
    *
    * @param id 主键ID
    * @return 结果
    */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(performanceWeightInfoService.getById(id));
    }

    /**
    * 查询绩效评价权重列表
    *
    * @return 结果
    */
    @GetMapping("/list")
    public R list() {
        return R.ok(performanceWeightInfoService.list());
    }

    /**
    * 新增绩效评价权重
    *
    * @param addFrom 绩效评价权重对象
    * @return 结果
    */
    @PostMapping
    public R save(@RequestBody PerformanceWeightInfo addFrom) {
        return R.ok(performanceWeightInfoService.save(addFrom));
    }

    /**
    * 修改绩效评价权重
    *
    * @param editFrom 绩效评价权重对象
    * @return 结果
    */
    @PutMapping
    public R edit(@RequestBody PerformanceWeightInfo editFrom) {
        return R.ok(performanceWeightInfoService.updateById(editFrom));
    }

    /**
    * 删除绩效评价权重
    *
    * @param ids 删除的主键ID
    * @return 结果
    */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(performanceWeightInfoService.removeByIds(ids));
    }

}
