package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.LogisticsInfo;
import com.fank.f1k2.business.service.ILogisticsInfoService;
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
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(logisticsInfoService.getById(id));
    }

    /**
     * 查询采购订单物流列表
     *
     * @return 结果
     */
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
    @PutMapping
    public R edit(@RequestBody LogisticsInfo editFrom) {
        // 更新订单其他物流记录为0
        logisticsInfoService.update(Wrappers.<LogisticsInfo>lambdaUpdate().set(LogisticsInfo::getCurrentLogistics, 0).eq(LogisticsInfo::getOrderId, editFrom.getOrderId()));
        // 更新新物流
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
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(logisticsInfoService.removeByIds(ids));
    }

}
