package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.LogisticsInfo;
import com.fank.f1k2.business.service.ILogisticsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 采购订单物流 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "采购订单物流")
@RestController
@RequestMapping("/business/logistics-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogisticsInfoController {

    private final ILogisticsInfoService logisticsInfoService;

    /**
     * 分页获取采购订单物流
     *
     * @param page      分页对象
     * @param queryFrom 采购订单物流
     * @return 结果
     */
    @ApiOperation(value = "分页查询采购订单物流", notes = "根据分页和筛选条件获取采购订单物流信息")
    @GetMapping("/page")
    public R page(Page<LogisticsInfo> page, LogisticsInfo queryFrom) {
        return R.ok(logisticsInfoService.queryPage(page, queryFrom));
    }

    /**
     * 根据订单ID查询采购订单物流
     *
     * @param orderId 订单ID
     * @return 采购订单物流列表
     */
    @ApiOperation(value = "根据订单ID查询物流", notes = "通过订单ID查询该订单下的所有物流记录")
    @GetMapping("/queryLogisticsByOrderId")
    public R queryLogisticsByOrderId(Integer orderId) {
        return R.ok(logisticsInfoService.list(Wrappers.<LogisticsInfo>lambdaQuery().eq(LogisticsInfo::getOrderId, orderId)));
    }

    /**
     * 查询采购订单物流详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "采购订单物流详情", notes = "通过ID获取采购订单物流详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(logisticsInfoService.getById(id));
    }

    /**
     * 查询采购订单物流列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取采购订单物流列表", notes = "列出所有采购订单物流信息")
    @GetMapping("/list")
    public R list() {
        return R.ok(logisticsInfoService.list());
    }

    /**
     * 新增采购订单物流
     *
     * @param addFrom 采购订单物流对象
     * @return 结果
     */
    @ApiOperation(value = "新增采购订单物流", notes = "创建一个新的采购订单物流记录，并设置为当前最新物流")
    @PostMapping
    public R save(@RequestBody LogisticsInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        addFrom.setCurrentLogistics(1);
        return R.ok(logisticsInfoService.save(addFrom));
    }

    /**
     * 修改采购订单物流
     *
     * @param editFrom 采购订单物流对象
     * @return 结果
     */
    @ApiOperation(value = "修改采购订单物流", notes = "更新已有的物流记录并重置当前物流标记")
    @PutMapping
    public R edit(@RequestBody LogisticsInfo editFrom) {
        logisticsInfoService.update(Wrappers.<LogisticsInfo>lambdaUpdate()
                .set(LogisticsInfo::getCurrentLogistics, 0)
                .eq(LogisticsInfo::getOrderId, editFrom.getOrderId()));
        editFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        editFrom.setCurrentLogistics(1);
        return R.ok(logisticsInfoService.save(editFrom));
    }

    /**
     * 删除采购订单物流
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除采购订单物流", notes = "根据ID集合批量删除采购订单物流记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(logisticsInfoService.removeByIds(ids));
    }
}
