package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.service.ISupplierInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 供应商信息 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/supplier-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierInfoController {

    private final ISupplierInfoService supplierInfoService;

    /**
     * 分页获取供应商信息
     *
     * @param page      分页对象
     * @param queryFrom 供应商信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SupplierInfo> page, SupplierInfo queryFrom) {
        return R.ok(supplierInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询供应商信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(supplierInfoService.getById(id));
    }

    /**
     * 查询供应商信息详情
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(supplierInfoService.list());
    }

    /**
     * 新增供应商信息
     *
     * @param addFrom 供应商信息对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody SupplierInfo addFrom) {
        addFrom.setCode("SUP-" + System.currentTimeMillis());
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(supplierInfoService.save(addFrom));
    }

    /**
     * 修改供应商信息
     *
     * @param editFrom 供应商信息对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody SupplierInfo editFrom) {
        return R.ok(supplierInfoService.updateById(editFrom));
    }

    /**
     * 删除供应商信息
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(supplierInfoService.removeByIds(ids));
    }

}
