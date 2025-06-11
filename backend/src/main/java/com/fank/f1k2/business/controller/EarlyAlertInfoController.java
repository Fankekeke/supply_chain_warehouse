package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EarlyAlertInfo;
import com.fank.f1k2.business.service.IEarlyAlertInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 预警库存设置 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/early-alert-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EarlyAlertInfoController {

    private final IEarlyAlertInfoService earlyAlertInfoService;

    /**
     * 分页获取预警库存设置
     *
     * @param page      分页对象
     * @param queryFrom 预警库存设置
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<EarlyAlertInfo> page, EarlyAlertInfo queryFrom) {
        return R.ok(earlyAlertInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询预警库存设置详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(earlyAlertInfoService.getById(id));
    }

    /**
     * 查询预警库存设置列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(earlyAlertInfoService.list());
    }

    /**
     * 新增预警库存设置
     *
     * @param addFrom 预警库存设置对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody EarlyAlertInfo addFrom) throws F1k2Exception {
        // 判断此物料是否已经添加库存设置
        int count = earlyAlertInfoService.count(Wrappers.<EarlyAlertInfo>lambdaQuery().eq(EarlyAlertInfo::getMaterialsCode, addFrom.getMaterialsCode()));
        if (count > 0) {
            throw new F1k2Exception("此物料已添加库存设置");
        }
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(earlyAlertInfoService.save(addFrom));
    }

    /**
     * 修改预警库存设置
     *
     * @param editFrom 预警库存设置对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody EarlyAlertInfo editFrom) {
        return R.ok(earlyAlertInfoService.updateById(editFrom));
    }

    /**
     * 删除预警库存设置
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(earlyAlertInfoService.removeByIds(ids));
    }

}
