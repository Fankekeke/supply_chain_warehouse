package com.fank.f1k2.business.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.business.entity.MaterialsInfo;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.service.IMaterialsInfoService;
import com.fank.f1k2.business.service.ISupplierInfoService;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierMaterialsInfo;
import com.fank.f1k2.business.service.ISupplierMaterialsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商物料 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "供应商物料")
@RestController
@RequestMapping("/business/supplier-materials-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierMaterialsInfoController {

    private final ISupplierMaterialsInfoService supplierMaterialsInfoService;

    private final ISupplierInfoService supplierInfoService;

    private final IMaterialsInfoService materialsInfoService;

    /**
     * 分页获取供应商物料
     *
     * @param page      分页对象
     * @param queryFrom 供应商物料
     * @return 结果
     */
    @ApiOperation(value = "分页查询供应商物料", notes = "根据分页和筛选条件获取供应商物料信息")
    @GetMapping("/page")
    public R page(Page<SupplierMaterialsInfo> page, SupplierMaterialsInfo queryFrom) {
        return R.ok(supplierMaterialsInfoService.queryPage(page, queryFrom));
    }

    /**
     * 根据供应商ID查询供应商物料
     *
     * @param supplierId 供应商ID
     * @return 结果
     */
    @ApiOperation(value = "按供应商查物料", notes = "通过供应商ID获取对应的物料列表")
    @GetMapping("/queryMaterialsBySupplierId")
    public R queryMaterialsBySupplierId(Integer supplierId) {
        return R.ok(supplierMaterialsInfoService.queryMaterialsBySupplierId(supplierId));
    }

    /**
     * 批量设置供应商物料
     *
     * @return 批量设置结果
     */
    @ApiOperation(value = "批量设置供应商物料", notes = "批量设置供应商物料信息")
    @GetMapping("/setSupplierMaterialsBatch")
    public R setSupplierMaterialsBatch() {
        List<SupplierInfo> list = supplierInfoService.list();
        List<MaterialsInfo> materialsInfoList = materialsInfoService.list();
        List<String> materialIds = materialsInfoList.stream().map(MaterialsInfo::getCode).collect(Collectors.toList());

        List<SupplierMaterialsInfo> addList = new ArrayList<>();
        for (SupplierInfo supplierInfo : list) {
            Set<String> set = RandomUtil.randomEleSet(materialIds, 8);
            for (String mid : set) {
                SupplierMaterialsInfo item = new SupplierMaterialsInfo();
                item.setSupplierId(supplierInfo.getId());
                item.setCode(mid);
                item.setPackageSet("统一封存");
                item.setBrand("品牌");
                item.setStatus("1");
                item.setCreateDate(DateUtil.formatDateTime(new Date()));
                addList.add(item);
            }
        }
        return R.ok( supplierMaterialsInfoService.saveBatch(addList));
    }

    /**
     * 查询供应商物料详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "供应商物料详情", notes = "通过ID获取供应商物料详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(supplierMaterialsInfoService.getById(id));
    }

    /**
     * 查询供应商物料列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取供应商物料列表", notes = "列出所有供应商物料记录")
    @GetMapping("/list")
    public R list() {
        return R.ok(supplierMaterialsInfoService.list());
    }

    /**
     * 新增供应商物料
     *
     * @param addFrom 供应商物料对象
     * @return 结果
     */
    @ApiOperation(value = "新增供应商物料", notes = "创建一个新的供应商物料记录，并自动绑定供应商信息")
    @PostMapping
    public R save(SupplierMaterialsInfo addFrom) {
        SupplierInfo supplierInfo = supplierInfoService.getOne(Wrappers.<SupplierInfo>lambdaQuery()
                .eq(SupplierInfo::getSysUserId, addFrom.getSupplierId()));
        if (supplierInfo != null) {
            addFrom.setSupplierId(supplierInfo.getId());
        }
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(supplierMaterialsInfoService.save(addFrom));
    }

    /**
     * 修改供应商物料
     *
     * @param editFrom 供应商物料对象
     * @return 结果
     */
    @ApiOperation(value = "修改供应商物料", notes = "更新已有的供应商物料信息")
    @PutMapping
    public R edit(SupplierMaterialsInfo editFrom) {
        return R.ok(supplierMaterialsInfoService.updateById(editFrom));
    }

    /**
     * 删除供应商物料
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除供应商物料", notes = "根据ID集合批量删除供应商物料记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(supplierMaterialsInfoService.removeByIds(ids));
    }

}
