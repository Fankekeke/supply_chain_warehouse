package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AbnormalInfo;
import com.fank.f1k2.business.service.IAbnormalInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "异常反馈")
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
    @ApiOperation(value = "分页获取异常反馈", notes = "分页获取异常反馈")
    @GetMapping("/page")
    public R page(Page<AbnormalInfo> page, AbnormalInfo queryFrom) {
        return R.ok(abnormalInfoService.queryPage(page, queryFrom));
    }

    /**
     * 根据订单ID查询异常反馈
     *
     * @param orderId 订单ID
     * @return 列表
     */
    @ApiOperation(value = "根据订单ID查询异常反馈", notes = "根据订单ID查询异常反馈")
    @GetMapping("/queryAbnormalByOrderId")
    public R queryAbnormalByOrderId(String orderId) {
        return R.ok(abnormalInfoService.list(Wrappers.<AbnormalInfo>lambdaQuery().eq(AbnormalInfo::getOrderId, orderId)));
    }

    /**
     * 查询异常反馈详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "查询异常反馈详情", notes = "查询异常反馈详情")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(abnormalInfoService.getById(id));
    }

    /**
     * 查询异常反馈列表
     *
     * @return 结果
     */
    @ApiOperation(value = "查询异常反馈列表", notes = "查询异常反馈列表")
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
    @ApiOperation(value = "新增异常反馈", notes = "新增异常反馈")
    @PostMapping
    public R save(AbnormalInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(abnormalInfoService.save(addFrom));
    }

    /**
     * 修改异常反馈
     *
     * @param editFrom 异常反馈对象
     * @return 结果
     */
    @ApiOperation(value = "修改异常反馈", notes = "修改异常反馈")
    @PutMapping
    public R edit(AbnormalInfo editFrom) {
        return R.ok(abnormalInfoService.updateById(editFrom));
    }

    /**
     * 删除异常反馈
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除异常反馈", notes = "删除异常反馈")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(abnormalInfoService.removeByIds(ids));
    }

}
