package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AgencyInfo;
import com.fank.f1k2.business.service.IAgencyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 代办任务 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "代办任务")
@RestController
@RequestMapping("/business/agency-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgencyInfoController {

    private final IAgencyInfoService agencyInfoService;

    /**
     * 分页获取代办任务-供应商
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    @ApiOperation(value = "分页获取代办任务-供应商", notes = "分页获取代办任务-供应商")
    @GetMapping("/page/supplier")
    public R queryPageBySupplier(Page<AgencyInfo> page, AgencyInfo queryFrom) {
        return R.ok(agencyInfoService.queryPageBySupplier(page, queryFrom));
    }

    /**
     * 分页获取代办任务-员工
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    @ApiOperation(value = "分页获取代办任务-员工", notes = "分页获取代办任务-员工")
    @GetMapping("/page/staff")
    public R queryPageByStaff(Page<AgencyInfo> page, AgencyInfo queryFrom) {
        return R.ok(agencyInfoService.queryPageByStaff(page, queryFrom));
    }

    /**
     * 代办任务完成
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "代办任务完成", notes = "代办任务完成")
    @GetMapping("/finish")
    public R agencyTaskFinish(Integer id) {
        return R.ok(agencyInfoService.update(Wrappers.<AgencyInfo>lambdaUpdate().set(AgencyInfo::getStatus, "1").set(AgencyInfo::getFinishDate, DateUtil.formatDateTime(new Date())).eq(AgencyInfo::getId, id)));
    }

    /**
     * 查询代办任务详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "查询代办任务详情", notes = "查询代办任务详情")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(agencyInfoService.getById(id));
    }

    /**
     * 查询代办任务列表
     *
     * @return 结果
     */
    @ApiOperation(value = "查询代办任务列表", notes = "查询代办任务列表")
    @GetMapping("/list")
    public R list() {
        return R.ok(agencyInfoService.list());
    }

    /**
     * 新增代办任务
     *
     * @param addFrom 代办任务对象
     * @return 结果
     */
    @ApiOperation(value = "新增代办任务", notes = "新增代办任务")
    @PostMapping
    public R save(@RequestBody AgencyInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(agencyInfoService.save(addFrom));
    }

    /**
     * 修改代办任务
     *
     * @param editFrom 代办任务对象
     * @return 结果
     */
    @ApiOperation(value = "修改代办任务", notes = "修改代办任务")
    @PutMapping
    public R edit(@RequestBody AgencyInfo editFrom) {
        return R.ok(agencyInfoService.updateById(editFrom));
    }

    /**
     * 删除代办任务
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除代办任务", notes = "删除代办任务")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(agencyInfoService.removeByIds(ids));
    }

}
