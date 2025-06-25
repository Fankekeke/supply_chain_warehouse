package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehouseInfo;
import com.fank.f1k2.business.service.IWarehouseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "库房库存")
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
    @ApiOperation(value = "分页查询库房库存", notes = "根据分页和筛选条件获取库房库存信息")
    @GetMapping("/page")
    public R page(Page<WarehouseInfo> page, WarehouseInfo queryFrom) {
        return R.ok(warehouseInfoService.queryPage(page, queryFrom));
    }

    /**
     * 分页获取预警库存
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    @ApiOperation(value = "分页查询预警库存", notes = "根据分页和筛选条件获取预警库存信息")
    @GetMapping("/queryAlertStockPage")
    public R queryAlertStockPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom) {
        return R.ok(warehouseInfoService.queryAlertStockPage(page, queryFrom));
    }

    /**
     * 分页获取出入库详情
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    @ApiOperation(value = "分页查询出入库详情", notes = "根据分页和筛选条件获取出入库详情信息")
    @GetMapping("/queryStockDetailPage")
    public R queryStockDetailPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom) {
        return R.ok(warehouseInfoService.queryStockDetailPage(page, queryFrom));
    }

    /**
     * 查询入库库存记录
     *
     * @param code 入库编码
     * @return 列表
     */
    @ApiOperation(value = "查询入库库存记录", notes = "根据编码查询入库库存记录")
    @GetMapping("/queryPutRecordDetail")
    public R queryPutRecordDetail(@RequestParam(value = "code") String code) {
        return R.ok(warehouseInfoService.queryPutRecordDetail(code));
    }

    /**
     * 查询出库库存记录
     *
     * @param code 出库编码
     * @return 列表
     */
    @ApiOperation(value = "查询出库库存记录", notes = "根据编码查询出库库存记录")
    @GetMapping("/queryOutRecordDetail")
    public R queryOutRecordDetail(@RequestParam(value = "code") String code) {
        return R.ok(warehouseInfoService.queryOutRecordDetail(code));
    }

    /**
     * 查询库房库存详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "库房库存详情", notes = "通过ID获取库房库存详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(warehouseInfoService.getById(id));
    }

    /**
     * 查询库房库存列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取库房库存列表", notes = "列出所有库房库存记录")
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
    @ApiOperation(value = "新增库房库存", notes = "创建一个新的库房库存记录")
    @PostMapping
    public R save(WarehouseInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(warehouseInfoService.save(addFrom));
    }

    /**
     * 修改库房库存
     *
     * @param editFrom 库房库存对象
     * @return 结果
     */
    @ApiOperation(value = "修改库房库存", notes = "更新已有的库房库存信息")
    @PutMapping
    public R edit(WarehouseInfo editFrom) {
        return R.ok(warehouseInfoService.updateById(editFrom));
    }

    /**
     * 删除库房库存
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除库房库存", notes = "根据ID集合批量删除库房库存记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(warehouseInfoService.removeByIds(ids));
    }
}
