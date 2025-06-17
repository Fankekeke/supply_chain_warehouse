package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.service.ISupplierInfoService;
import com.fank.f1k2.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "供应商信息")
@RestController
@RequestMapping("/business/supplier-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierInfoController {

    private final ISupplierInfoService supplierInfoService;

    private final UserService userService;

    /**
     * 分页获取供应商信息
     *
     * @param page      分页对象
     * @param queryFrom 供应商信息
     * @return 结果
     */
    @ApiOperation(value = "分页查询供应商", notes = "根据分页和筛选条件获取供应商信息")
    @GetMapping("/page")
    public R page(Page<SupplierInfo> page, SupplierInfo queryFrom) {
        return R.ok(supplierInfoService.queryPage(page, queryFrom));
    }

    /**
     * 根据系统用户ID查询供应商
     *
     * @param sysUserId 系统用户ID
     * @return 供应商列表
     */
    @ApiOperation(value = "按用户ID查供应商", notes = "通过系统用户ID获取对应的供应商信息")
    @GetMapping("/querySupplierBySysUserId")
    public R querySupplierBySysUserId(Integer sysUserId) {
        return R.ok(supplierInfoService.getOne(Wrappers.<SupplierInfo>lambdaQuery()
                .eq(SupplierInfo::getSysUserId, sysUserId)));
    }

    /**
     * 查询匹配的供应商信息
     *
     * @return 列表
     */
    @ApiOperation(value = "查询匹配供应商", notes = "根据物料编码查询可匹配的供应商信息")
    @GetMapping("/querySupplierByMatch")
    public R querySupplierByMatch(String materialsCode) {
        return R.ok(supplierInfoService.querySupplierByMatch(materialsCode));
    }

    /**
     * 查询供应商信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "供应商详情", notes = "通过ID获取供应商详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(supplierInfoService.getById(id));
    }

    /**
     * 查询供应商信息列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取供应商集合", notes = "列出所有供应商记录")
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
    @ApiOperation(value = "新增供应商", notes = "创建一个新的供应商信息并设置初始状态为未启用")
    @PostMapping
    public R save(@RequestBody SupplierInfo addFrom) {
        addFrom.setCode("SUP-" + System.currentTimeMillis());
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        addFrom.setStatus("0");
        return R.ok(supplierInfoService.save(addFrom));
    }

    /**
     * 修改供应商信息
     *
     * @param editFrom 供应商信息对象
     * @return 结果
     */
    @ApiOperation(value = "修改供应商信息", notes = "更新已有的供应商信息")
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
    @ApiOperation(value = "删除供应商", notes = "根据ID集合批量删除供应商记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(supplierInfoService.removeByIds(ids));
    }

}
