package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehouseOutRecord;
import com.fank.f1k2.business.service.IWarehouseOutRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 库存出库记录 控制层
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/warehouse-out-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WarehouseOutRecordController {

    private final IWarehouseOutRecordService bulletinInfoService;

    /**
    * 分页获取库存出库记录
    *
    * @param page       分页对象
    * @param queryFrom 库存出库记录
    * @return 结果
    */
    @GetMapping("/page")
    public R page(Page<WarehouseOutRecord> page, WarehouseOutRecord queryFrom) {
        return R.ok();
    }

    /**
    * 查询库存出库记录详情
    *
    * @param id 主键ID
    * @return 结果
    */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    /**
    * 查询库存出库记录列表
    *
    * @return 结果
    */
    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
    * 新增库存出库记录
    *
    * @param addFrom 库存出库记录对象
    * @return 结果
    */
    @PostMapping
    public R save(@RequestBody WarehouseOutRecord addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(addFrom));
    }

    /**
    * 修改库存出库记录
    *
    * @param editFrom 库存出库记录对象
    * @return 结果
    */
    @PutMapping
    public R edit(@RequestBody WarehouseOutRecord editFrom) {
        return R.ok(bulletinInfoService.updateById(editFrom));
    }

    /**
    * 删除库存出库记录
    *
    * @param ids 删除的主键ID
    * @return 结果
    */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }

}
