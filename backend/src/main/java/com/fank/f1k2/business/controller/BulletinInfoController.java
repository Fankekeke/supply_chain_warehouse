package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.BulletinInfo;
import com.fank.f1k2.business.service.IBulletinInfoService;
import com.fank.f1k2.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "公告信息")
@RestController
@RequestMapping("/business/bulletin-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinInfoController {

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取公告信息
     *
     * @param page         分页对象
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @ApiOperation(value = "分页查询公告", notes = "根据分页和筛选条件获取公告信息")
    @GetMapping("/page")
    public R page(Page<BulletinInfo> page, BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.getBulletinByPage(page, bulletinInfo));
    }

    @ApiOperation(value = "公告详情", notes = "通过ID获取公告详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    @ApiOperation(value = "获取公告列表", notes = "列出所有公告信息")
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
    @ApiOperation(value = "新增公告", notes = "创建一个新的公告信息")
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
    @ApiOperation(value = "修改公告", notes = "根据传入的公告信息更新已有的公告")
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
    @ApiOperation(value = "删除公告", notes = "根据ID集合批量删除公告")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }
}
