package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.fank.f1k2.business.service.IPurchaseQuotationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 采购计划报价管理 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/purchase-quotation-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseQuotationInfoController {

    private final IPurchaseQuotationInfoService purchaseQuotationInfoService;

    /**
     * 分页获取采购计划报价管理
     *
     * @param page      分页对象
     * @param queryFrom 采购计划报价管理
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PurchaseQuotationInfo> page, PurchaseQuotationInfo queryFrom) {
        return R.ok(purchaseQuotationInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询采购计划报价管理详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(purchaseQuotationInfoService.getById(id));
    }

    /**
     * 查询采购计划报价管理列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(purchaseQuotationInfoService.list());
    }

    /**
     * 新增采购计划报价管理
     *
     * @param addFrom 采购计划报价管理对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody PurchaseQuotationInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(purchaseQuotationInfoService.save(addFrom));
    }

    /**
     * 修改采购计划报价管理
     *
     * @param editFrom 采购计划报价管理对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody PurchaseQuotationInfo editFrom) {
        return R.ok(purchaseQuotationInfoService.updateById(editFrom));
    }

    /**
     * 删除采购计划报价管理
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(purchaseQuotationInfoService.removeByIds(ids));
    }

}
