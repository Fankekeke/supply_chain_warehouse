package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.business.entity.EarlyAlertInfo;
import com.fank.f1k2.business.service.IEarlyAlertInfoService;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.MaterialsInfo;
import com.fank.f1k2.business.service.IMaterialsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 物料管理 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "物料管理")
@RestController
@RequestMapping("/business/materials-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MaterialsInfoController {

    private final IMaterialsInfoService materialsInfoService;

    private final IEarlyAlertInfoService earlyAlertInfoService;

    /**
     * 分页获取物料管理
     *
     * @param page      分页对象
     * @param queryFrom 物料管理
     * @return 结果
     */
    @ApiOperation(value = "分页查询物料", notes = "根据分页和筛选条件获取物料信息")
    @GetMapping("/page")
    public R page(Page<MaterialsInfo> page, MaterialsInfo queryFrom) {
        return R.ok(materialsInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询物料管理详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "物料详情", notes = "通过ID获取物料详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(materialsInfoService.getById(id));
    }

    /**
     * 查询物料管理列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取物料列表", notes = "列出所有物料信息")
    @GetMapping("/list")
    public R list() {
        return R.ok(materialsInfoService.list());
    }

    /**
     * 新增物料管理
     *
     * @param addFrom 物料管理对象
     * @return 结果
     */
    @ApiOperation(value = "新增物料", notes = "创建一个新的物料并同步创建对应的预警设置")
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public R save(MaterialsInfo addFrom) {
        addFrom.setCode("MAT-" + System.currentTimeMillis());
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));

        EarlyAlertInfo earlyAlertInfo = new EarlyAlertInfo();
        earlyAlertInfo.setMaterialsCode(addFrom.getCode());
        earlyAlertInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        if (addFrom.getMinValue() != null) {
            earlyAlertInfo.setMinValue(addFrom.getMinValue());
        } else {
            earlyAlertInfo.setMinValue(-1);
        }
        earlyAlertInfoService.save(earlyAlertInfo);
        return R.ok(materialsInfoService.save(addFrom));
    }

    /**
     * 批量新增预警库存
     *
     * @return 批量结果
     */
    @ApiOperation(value = "批量新增预警库存", notes = "批量创建预警库存设置")
    @PostMapping("/batchAddAlert")
    public R batchAddAlert() {
        // 获取所有物料信息
        List<MaterialsInfo> materialsInfoList = materialsInfoService.list();
        List<EarlyAlertInfo> earlyAlertInfoList = new ArrayList<>();
        for (MaterialsInfo materialsInfo : materialsInfoList) {
            EarlyAlertInfo earlyAlertInfo = new EarlyAlertInfo();
            earlyAlertInfo.setMaterialsCode(materialsInfo.getCode());
            earlyAlertInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            earlyAlertInfo.setMinValue(-1);
            earlyAlertInfoList.add(earlyAlertInfo);
        }
        return R.ok(earlyAlertInfoService.saveBatch(earlyAlertInfoList));
    }

    /**
     * 修改物料管理
     *
     * @param editFrom 物料管理对象
     * @return 结果
     */
    @ApiOperation(value = "修改物料", notes = "更新已有的物料信息")
    @PutMapping
    public R edit(MaterialsInfo editFrom) {
        return R.ok(materialsInfoService.updateById(editFrom));
    }

    /**
     * 删除物料管理
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除物料", notes = "根据ID集合批量删除物料记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(materialsInfoService.removeByIds(ids));
    }
}
