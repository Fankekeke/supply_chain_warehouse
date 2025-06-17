package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.business.entity.AgencyInfo;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.NotifyInfo;
import com.fank.f1k2.business.service.INotifyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "消息通知")
@RestController
@RequestMapping("/business/notify-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotifyInfoController {

    private final INotifyInfoService notifyInfoService;

    /**
     * 分页获取消息通知-供应商
     *
     * @param page      分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    @ApiOperation(value = "分页查询供应商消息", notes = "根据分页和筛选条件获取供应商相关消息通知")
    @GetMapping("/page/supplier")
    public R queryPageBySupplier(Page<NotifyInfo> page, NotifyInfo queryFrom) {
        return R.ok(notifyInfoService.queryPageBySupplier(page, queryFrom));
    }

    /**
     * 分页获取消息通知-员工
     *
     * @param page      分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    @ApiOperation(value = "分页查询员工消息", notes = "根据分页和筛选条件获取员工相关消息通知")
    @GetMapping("/page/staff")
    public R queryPageByStaff(Page<NotifyInfo> page, NotifyInfo queryFrom) {
        return R.ok(notifyInfoService.queryPageByStaff(page, queryFrom));
    }

    /**
     * 消息通知已读
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "标记消息为已读", notes = "将指定ID的消息状态更新为已读，并记录完成时间")
    @GetMapping("/finish")
    public R notifyFinish(Integer id) {
        return R.ok(notifyInfoService.update(Wrappers.<NotifyInfo>lambdaUpdate()
                .set(NotifyInfo::getStatus, "1")
                .set(NotifyInfo::getFinishDate, DateUtil.formatDateTime(new Date()))
                .eq(NotifyInfo::getId, id)));
    }

    /**
     * 查询消息通知详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "消息详情", notes = "通过ID获取消息通知详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(notifyInfoService.getById(id));
    }

    /**
     * 查询消息通知列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取消息列表", notes = "列出所有消息通知信息")
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
    @ApiOperation(value = "新增消息通知", notes = "创建一个新的消息通知记录")
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
    @ApiOperation(value = "修改消息通知", notes = "更新已有的消息通知信息")
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
    @ApiOperation(value = "删除消息通知", notes = "根据ID集合批量删除消息通知记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(notifyInfoService.removeByIds(ids));
    }
}
