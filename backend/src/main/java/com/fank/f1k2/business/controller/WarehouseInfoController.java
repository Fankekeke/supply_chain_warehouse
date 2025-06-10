package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehouseInfo;
import com.fank.f1k2.business.service.IWarehouseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 库房库存 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/warehouse-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WarehouseInfoController {

    private final IWarehouseInfoService warehouseInfoService;

    /**
     * 分页获取库房库存
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<WarehouseInfo> page, WarehouseInfo queryFrom) {
        return R.ok(warehouseInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询库房库存详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(warehouseInfoService.getById(id));
    }

    /**
     * 查询库房库存列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(warehouseInfoService.list());
    }

    /**
     * 新增库房库存
     *
     * @param addFrom 库房库存对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody WarehouseInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(warehouseInfoService.save(addFrom));
    }

    /**
     * 修改库房库存
     *
     * @param editFrom 库房库存对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody WarehouseInfo editFrom) {
        return R.ok(warehouseInfoService.updateById(editFrom));
    }

    /**
     * 删除库房库存
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(warehouseInfoService.removeByIds(ids));
    }

}
