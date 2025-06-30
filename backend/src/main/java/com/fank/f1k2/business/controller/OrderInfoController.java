package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.business.entity.LogisticsInfo;
import com.fank.f1k2.business.entity.PurchasePlanInfo;
import com.fank.f1k2.business.service.ILogisticsInfoService;
import com.fank.f1k2.business.service.IPurchasePlanInfoService;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.OrderInfo;
import com.fank.f1k2.business.service.IOrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 采购订单 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "采购订单")
@RestController
@RequestMapping("/business/order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    private final IPurchasePlanInfoService purchasePlanInfoService;

    private final ILogisticsInfoService logisticsInfoService;

    /**
     * 分页获取采购订单
     *
     * @param page      分页对象
     * @param queryFrom 采购订单
     * @return 结果
     */
    @ApiOperation(value = "分页查询采购订单", notes = "根据分页和筛选条件获取采购订单信息")
    @GetMapping("/page")
    public R page(Page<OrderInfo> page, OrderInfo queryFrom) {
        return R.ok(orderInfoService.queryPage(page, queryFrom));
    }

    /**
     * 设置采购订单状态
     *
     * @param id     主键ID
     * @param status 状态
     * @return 结果
     */
    @ApiOperation(value = "设置订单状态", notes = "更新指定ID的采购订单状态")
    @GetMapping("/setOrderStatus")
    public R setOrderStatus(Integer id, String status) {
        return R.ok(orderInfoService.setOrderStatus(id, status));
    }

    /**
     * 统计数据
     *
     * @return 结果
     */
    @ApiOperation(value = "统计数据", notes = "统计数据")
    @GetMapping("/home/data")
    public R selectHomeData() {
        return R.ok(orderInfoService.homeData());
    }

    /**
     * 供应商统计数据
     *
     * @param userId 用户ID
     * @return 结果
     */
    @ApiOperation(value = "供应商统计数据", notes = "统计供应商数据")
    @GetMapping("/home/data/supplier")
    public R selectHomeDataBySupplier(Integer userId) {
        return R.ok(orderInfoService.homeDataBySupplier(userId));
    }

    /**
     * 查询采购订单详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "采购订单详情", notes = "通过ID获取采购订单详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(orderInfoService.getById(id));
    }

    /**
     * 查询采购订单列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取采购订单列表", notes = "列出所有采购订单信息")
    @GetMapping("/list")
    public R list() {
        return R.ok(orderInfoService.list());
    }

    /**
     * 采购订单付款
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @ApiOperation(value = "采购订单付款", notes = "付款")
    @GetMapping("/payment")
    public R payment(String orderCode) {
        OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, orderCode));
        return R.ok(orderInfoService.setOrderStatus(orderInfo.getId(), "1"));
    }

    /**
     * 采购订单发货
     *
     * @param orderId 订单编号
     * @return 结果
     */
    @ApiOperation(value = "采购订单发货", notes = "发货")
    @GetMapping("/ship")
    @Transactional(rollbackFor = Exception.class)
    public R orderShip(Integer orderId, String content) {
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        LogisticsInfo logisticsInfo = new LogisticsInfo();
        logisticsInfo.setOrderId(orderId);
        logisticsInfo.setRemark(content);
        logisticsInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        logisticsInfo.setCurrentLogistics(1);
        logisticsInfoService.save(logisticsInfo);
        return R.ok(orderInfoService.setOrderStatus(orderInfo.getId(), "2"));
    }

    /**
     * 修改采购订单物流信息
     *
     * @param orderId 订单编号
     * @return 结果
     */
    @ApiOperation(value = "修改采购订单物流信息", notes = "修改采购订单物流信息")
    @GetMapping("/updateOrderLogistics")
    public R updateOrderLogistics(Integer orderId, String content) {
        // 更新其他物流
        logisticsInfoService.update(Wrappers.<LogisticsInfo>lambdaUpdate().set(LogisticsInfo::getCurrentLogistics, 0).eq(LogisticsInfo::getOrderId, orderId));
        LogisticsInfo logisticsInfo = new LogisticsInfo();
        logisticsInfo.setOrderId(orderId);
        logisticsInfo.setRemark(content);
        logisticsInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        logisticsInfo.setCurrentLogistics(1);
        return R.ok(logisticsInfoService.save(logisticsInfo));
    }

    /**
     * 新增采购订单
     *
     * @param addFrom 采购订单对象
     * @return 结果
     */
    @ApiOperation(value = "新增采购订单", notes = "创建一个新的采购订单记录")
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public R save(OrderInfo addFrom) {
        addFrom.setCode("ORD-" + System.currentTimeMillis());
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        addFrom.setStatus("0");
        // 获取采购计划
        PurchasePlanInfo purchasePlanInfo = purchasePlanInfoService.getById(addFrom.getPlanId());
        purchasePlanInfo.setStatus("3");
        purchasePlanInfoService.updateById(purchasePlanInfo);
        return R.ok(orderInfoService.save(addFrom));
    }

    /**
     * 修改采购订单
     *
     * @param editFrom 采购订单对象
     * @return 结果
     */
    @ApiOperation(value = "修改采购订单", notes = "更新已有的采购订单信息")
    @PutMapping
    public R edit(OrderInfo editFrom) {
        return R.ok(orderInfoService.updateById(editFrom));
    }

    /**
     * 删除采购订单
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除采购订单", notes = "根据ID集合批量删除采购订单记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(orderInfoService.removeByIds(ids));
    }
}
