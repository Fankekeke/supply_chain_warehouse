package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.BulletinInfo;
import com.fank.f1k2.business.service.IBulletinInfoService;
import com.fank.f1k2.common.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 公共信息 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/bulletin-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinInfoController {

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取公告信息
     *
     * @param page 分页对象
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<BulletinInfo> page, BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.getBulletinByPage(page, bulletinInfo));
    }

    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
     * 新增公告信息
     *
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @PostMapping
    public R save(BulletinInfo bulletinInfo) {
        bulletinInfo.setDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(bulletinInfo));
    }

    /**
     * 修改公告信息
     *
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @PutMapping
    public R edit(BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.updateById(bulletinInfo));
    }

    /**
     * 删除公告信息
     *
     * @param ids ids
     * @return 公告信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }
}
