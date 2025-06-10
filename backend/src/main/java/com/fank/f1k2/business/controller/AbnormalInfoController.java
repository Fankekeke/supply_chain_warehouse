package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AbnormalInfo;
import com.fank.f1k2.business.service.IAbnormalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 异常反馈 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/abnormal-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AbnormalInfoController {

    private final IAbnormalInfoService abnormalInfoService;

    /**
     * 分页获取异常反馈
     *
     * @param page      分页对象
     * @param queryFrom 异常反馈
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<AbnormalInfo> page, AbnormalInfo queryFrom) {
        return R.ok(abnormalInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询异常反馈详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(abnormalInfoService.getById(id));
    }

    /**
     * 查询异常反馈列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(abnormalInfoService.list());
    }

    /**
     * 新增异常反馈
     *
     * @param addFrom 异常反馈对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody AbnormalInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(abnormalInfoService.save(addFrom));
    }

    /**
     * 修改异常反馈
     *
     * @param editFrom 异常反馈对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody AbnormalInfo editFrom) {
        return R.ok(abnormalInfoService.updateById(editFrom));
    }

    /**
     * 删除异常反馈
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(abnormalInfoService.removeByIds(ids));
    }

}
