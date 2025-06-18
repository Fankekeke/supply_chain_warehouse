package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.business.service.IWarehouseInfoService;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehouseOutRecord;
import com.fank.f1k2.business.service.IWarehouseOutRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 库存出库记录 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "库存出库记录")
@RestController
@RequestMapping("/business/warehouse-out-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WarehouseOutRecordController {

    private final IWarehouseOutRecordService warehouseOutRecordService;

    /**
     * 分页获取库存出库记录
     *
     * @param page      分页对象
     * @param queryFrom 库存出库记录
     * @return 结果
     */
    @ApiOperation(value = "分页查询出库记录", notes = "根据分页和筛选条件获取库存出库记录信息")
    @GetMapping("/page")
    public R page(Page<WarehouseOutRecord> page, WarehouseOutRecord queryFrom) {
        return R.ok(warehouseOutRecordService.queryPage(page, queryFrom));
    }

    /**
     * 查询库存出库记录详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "出库记录详情", notes = "通过ID获取库存出库记录详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(warehouseOutRecordService.getById(id));
    }

    /**
     * 查询库存出库记录列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取出库记录列表", notes = "列出所有库存出库记录")
    @GetMapping("/list")
    public R list() {
        return R.ok(warehouseOutRecordService.list());
    }

    /**
     * 新增库存出库记录
     *
     * @param addFrom 库存出库记录对象
     * @return 结果
     */
    @ApiOperation(value = "新增出库记录", notes = "创建一个新的库存出库记录")
    @PostMapping
    public R save(@RequestBody WarehouseOutRecord addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(warehouseOutRecordService.save(addFrom));
    }

    /**
     * 修改库存出库记录
     *
     * @param editFrom 库存出库记录对象
     * @return 结果
     */
    @ApiOperation(value = "修改出库记录", notes = "更新已有的库存出库记录信息")
    @PutMapping
    public R edit(@RequestBody WarehouseOutRecord editFrom) {
        return R.ok(warehouseOutRecordService.updateById(editFrom));
    }

    /**
     * 删除库存出库记录
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除出库记录", notes = "根据ID集合批量删除库存出库记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(warehouseOutRecordService.removeByIds(ids));
    }

}
