package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    /**
     * 分页获取采购订单
     *
     * @param page      分页对象
     * @param queryFrom 采购订单
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<OrderInfo> page, OrderInfo queryFrom);

    /**
     * 供应商订单数据
     *
     * @param supplierId 供应商ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryListBySupplier(Integer supplierId);

    /**
     * 设置采购订单状态
     *
     * @param id     主键ID
     * @param status 状态
     * @return 结果
     */
    boolean setOrderStatus(Integer id, String status);

    /**
     * 采购订单入库
     *
     * @param orderId 订单编号
     * @return 结果
     */
    boolean orderPutStock(Integer orderId);

    /**
     * 主页数据
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData();

    /**
     * 供应商统计数据
     *
     * @param userId 用户ID
     * @return 结果
     */
    LinkedHashMap<String, Object> homeDataBySupplier(Integer userId);
}
