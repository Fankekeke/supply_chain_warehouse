package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchasePlanInfo;
import com.fank.f1k2.business.service.IPurchasePlanInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 采购计划 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/purchase-plan-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchasePlanInfoController {

    private final IPurchasePlanInfoService bulletinInfoService;

    /**
     * 分页获取采购计划
     *
     * @param page      分页对象
     * @param queryFrom 采购计划
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PurchasePlanInfo> page, PurchasePlanInfo queryFrom) {
        return R.ok(bulletinInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询采购计划详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    /**
     * 查询采购计划列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
     * 新增采购计划
     *
     * @param addFrom 采购计划对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody PurchasePlanInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(addFrom));
    }

    /**
     * 修改采购计划
     *
     * @param editFrom 采购计划对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody PurchasePlanInfo editFrom) {
        return R.ok(bulletinInfoService.updateById(editFrom));
    }

    /**
     * 删除采购计划
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }

}
