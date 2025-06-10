package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.OrderInfo;
import com.fank.f1k2.business.service.IOrderInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 采购订单 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@RestController
@RequestMapping("/business/order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    /**
     * 分页获取采购订单
     *
     * @param page      分页对象
     * @param queryFrom 采购订单
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<OrderInfo> page, OrderInfo queryFrom) {
        return R.ok(orderInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询采购订单详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(orderInfoService.getById(id));
    }

    /**
     * 查询采购订单列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(orderInfoService.list());
    }

    /**
     * 新增采购订单
     *
     * @param addFrom 采购订单对象
     * @return 结果
     */
    @PostMapping
    public R save(@RequestBody OrderInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(orderInfoService.save(addFrom));
    }

    /**
     * 修改采购订单
     *
     * @param editFrom 采购订单对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody OrderInfo editFrom) {
        return R.ok(orderInfoService.updateById(editFrom));
    }

    /**
     * 删除采购订单
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(orderInfoService.removeByIds(ids));
    }

}
