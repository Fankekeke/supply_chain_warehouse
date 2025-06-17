package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.fank.f1k2.business.service.IPurchaseQuotationInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "采购计划报价管理")
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
    @ApiOperation(value = "分页查询采购计划报价", notes = "根据分页和筛选条件获取采购计划报价信息")
    @GetMapping("/page")
    public R page(Page<PurchaseQuotationInfo> page, PurchaseQuotationInfo queryFrom) {
        return R.ok(purchaseQuotationInfoService.queryPage(page, queryFrom));
    }

    /**
     * 根据采购计划ID查询采购计划报价
     *
     * @param planId 采购计划ID
     * @return 结果
     */
    @ApiOperation(value = "按计划ID查询报价", notes = "通过采购计划ID获取对应的报价信息")
    @GetMapping("/queryQuotationByPlanId")
    public R queryQuotationByPlanId(Integer planId) {
        return R.ok(purchaseQuotationInfoService.queryQuotationByPlanId(planId));
    }

    /**
     * 修改采购计划报价状态
     *
     * @param id     主键ID
     * @param status 采购计划报价状态
     * @return 结果
     */
    @ApiOperation(value = "设置报价状态", notes = "更新指定ID的采购计划报价状态")
    @GetMapping("/setQuotationStatus")
    public R setQuotationStatus(Integer id, String status) {
        return R.ok(purchaseQuotationInfoService.setQuotationStatus(id, status));
    }

    /**
     * 查询采购计划报价管理详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "采购计划报价详情", notes = "通过ID获取采购计划报价详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(purchaseQuotationInfoService.getById(id));
    }

    /**
     * 查询采购计划报价管理列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取报价列表", notes = "列出所有采购计划报价记录")
    @GetMapping("/list")
    public R list() {
        return R.ok(purchaseQuotationInfoService.list());
    }

    /**
     * 新增采购计划报价【采购计划下发】
     *
     * @param addFrom 采购计划报价管理对象
     * @return 结果
     */
    @ApiOperation(value = "新增采购计划报价", notes = "创建一个新的采购计划报价记录，并执行采购计划下发操作")
    @PostMapping
    public R save(@RequestBody PurchaseQuotationInfo addFrom) throws F1k2Exception {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(purchaseQuotationInfoService.purchasePlanIssue(addFrom));
    }

    /**
     * 修改采购计划报价管理
     *
     * @param editFrom 采购计划报价管理对象
     * @return 结果
     */
    @ApiOperation(value = "修改采购计划报价", notes = "更新已有的采购计划报价信息")
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
    @ApiOperation(value = "删除采购计划报价", notes = "根据ID集合批量删除采购计划报价记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(purchaseQuotationInfoService.removeByIds(ids));
    }

}
