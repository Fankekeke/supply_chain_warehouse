package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AgencyInfo;
import com.fank.f1k2.business.service.IAgencyInfoService;
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
@RestController
@RequestMapping("/business/agency-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgencyInfoController {

    private final IAgencyInfoService bulletinInfoService;

    /**
     * 分页获取代办任务
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<AgencyInfo> page, AgencyInfo queryFrom) {
        return R.ok(bulletinInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询代办任务详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    /**
     * 查询代办任务列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
     * 新增代办任务
     *
     * @param addFrom 代办任务对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody AgencyInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(addFrom));
    }

    /**
     * 修改代办任务
     *
     * @param editFrom 代办任务对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody AgencyInfo editFrom) {
        return R.ok(bulletinInfoService.updateById(editFrom));
    }

    /**
     * 删除代办任务
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }

}
