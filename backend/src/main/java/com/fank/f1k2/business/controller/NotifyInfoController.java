package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.NotifyInfo;
import com.fank.f1k2.business.service.INotifyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 消息通知 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/notify-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotifyInfoController {

    private final INotifyInfoService notifyInfoService;

    /**
     * 分页获取消息通知
     *
     * @param page      分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<NotifyInfo> page, NotifyInfo queryFrom) {
        return R.ok(notifyInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询消息通知详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(notifyInfoService.getById(id));
    }

    /**
     * 查询消息通知列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(notifyInfoService.list());
    }

    /**
     * 新增消息通知
     *
     * @param addFrom 消息通知对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody NotifyInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(notifyInfoService.save(addFrom));
    }

    /**
     * 修改消息通知
     *
     * @param editFrom 消息通知对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody NotifyInfo editFrom) {
        return R.ok(notifyInfoService.updateById(editFrom));
    }

    /**
     * 删除消息通知
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(notifyInfoService.removeByIds(ids));
    }

}
