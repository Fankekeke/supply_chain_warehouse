package com.fank.f1k2.business.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.service.ISupplierInfoService;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierAuditRecord;
import com.fank.f1k2.business.service.ISupplierAuditRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商审核记录 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "供应商审核记录")
@RestController
@RequestMapping("/business/supplier-audit-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierAuditRecordController {

    private final ISupplierAuditRecordService supplierAuditRecordService;

    private final ISupplierInfoService supplierInfoService;

    /**
     * 分页获取供应商审核记录
     *
     * @param page      分页对象
     * @param queryFrom 供应商审核记录
     * @return 结果
     */
    @ApiOperation(value = "分页查询供应商审核", notes = "根据分页和筛选条件获取供应商审核记录信息")
    @GetMapping("/page")
    public R page(Page<SupplierAuditRecord> page, SupplierAuditRecord queryFrom) {
        return R.ok(supplierAuditRecordService.queryPage(page, queryFrom));
    }

    /**
     * 供应商审核
     *
     * @param supplierAuditRecord 审核信息
     * @return 结果
     */
    @ApiOperation(value = "执行供应商审核", notes = "对供应商进行审核操作并保存审核记录")
    @PutMapping("/supplierAudit")
    public R supplierAudit(SupplierAuditRecord supplierAuditRecord) throws Exception {
        return R.ok(supplierAuditRecordService.supplierAudit(supplierAuditRecord));
    }

    /**
     * 批量设置供应商审核状态
     *
     * @return 批量设置结果
     */
    @GetMapping("/setSupplierAuditBatch")
    public R setSupplierAuditBatch() {
        List<SupplierInfo> supplierInfoList = supplierInfoService.list();
        if (CollectionUtil.isEmpty(supplierInfoList)) {
            return R.ok();
        }
        List<SupplierAuditRecord> supplierAuditRecordList = new ArrayList<>();
        int i = 0;
        for (SupplierInfo supplierInfo : supplierInfoList) {
            SupplierAuditRecord auditRecord = new SupplierAuditRecord();
            auditRecord.setSupplierId(supplierInfo.getId());
            auditRecord.setStatus("0");
            auditRecord.setCode("ASUP-" + "000" + i);
            auditRecord.setContent(supplierInfo.getName() + "审核内容");
            auditRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
            i++;
            supplierAuditRecordList.add(auditRecord);
        }
        return R.ok(supplierAuditRecordService.saveBatch(supplierAuditRecordList));
    }

    /**
     * 查询供应商审核记录详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "审核记录详情", notes = "通过ID获取供应商审核详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(supplierAuditRecordService.getById(id));
    }

    /**
     * 查询供应商审核记录列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取审核记录列表", notes = "列出所有供应商审核记录")
    @GetMapping("/list")
    public R list() {
        return R.ok(supplierAuditRecordService.list());
    }

    /**
     * 新增供应商审核记录
     *
     * @param addFrom 供应商审核记录对象
     * @return 结果
     */
    @ApiOperation(value = "新增审核记录", notes = "创建一个新的供应商审核记录")
    @PostMapping
    public R save(SupplierAuditRecord addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(supplierAuditRecordService.save(addFrom));
    }

    /**
     * 修改供应商审核记录
     *
     * @param editFrom 供应商审核记录对象
     * @return 结果
     */
    @ApiOperation(value = "修改审核记录", notes = "更新已有的供应商审核记录信息")
    @PutMapping
    public R edit(SupplierAuditRecord editFrom) {
        return R.ok(supplierAuditRecordService.updateById(editFrom));
    }

    /**
     * 删除供应商审核记录
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除审核记录", notes = "根据ID集合批量删除供应商审核记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(supplierAuditRecordService.removeByIds(ids));
    }

}
