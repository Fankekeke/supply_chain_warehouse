package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.BulletinInfo;
import com.fank.f1k2.business.entity.OrderInfo;
import com.fank.f1k2.business.dao.OrderInfoMapper;
import com.fank.f1k2.business.entity.StaffInfo;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.service.IBulletinInfoService;
import com.fank.f1k2.business.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.business.service.IStaffInfoService;
import com.fank.f1k2.business.service.ISupplierInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IBulletinInfoService bulletinInfoService;

    private final ISupplierInfoService supplierInfoService;

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取采购订单
     *
     * @param page      分页对象
     * @param queryFrom 采购订单
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<OrderInfo> page, OrderInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 设置采购订单状态
     *
     * @param id     主键ID
     * @param status 状态
     * @return 结果
     */
    @Override
    public boolean setOrderStatus(Integer id, String status) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(id);
        orderInfo.setStatus(status);
        // TODO 采购状态更新发送消息或者代办

        // 更新采购订单状态
        return updateById(orderInfo);
    }

    /**
     * 主页数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 总订单数量
        result.put("orderCode", this.count());
        // 总支出
        result.put("orderPrice", baseMapper.selectOrderPrice());
        // 店铺数量
        result.put("pharmacyNum", supplierInfoService.count(Wrappers.<SupplierInfo>lambdaQuery().eq(SupplierInfo::getStatus, 1)));
        // 员工数量
        result.put("staffNum", staffInfoService.count());

        // 本月订单数量
        List<OrderInfo> orderList = baseMapper.selectOrderByMonth();
        result.put("monthOrderNum", CollectionUtil.isEmpty(orderList) ? 0 : orderList.size());
        BigDecimal orderPrice = orderList.stream().filter(e -> Integer.parseInt(e.getStatus()) >= 1 && Integer.parseInt(e.getStatus()) != 4).map(OrderInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 获取本月支出
        result.put("monthOrderPrice", orderPrice);

        // 本年订单数量
        List<OrderInfo> orderYearList = baseMapper.selectOrderByYear();
        result.put("yearOrderNum", CollectionUtil.isEmpty(orderYearList) ? 0 : orderYearList.size());
        // 本年总支出
        BigDecimal orderYearPrice = orderYearList.stream().filter(e -> Integer.parseInt(e.getStatus()) >= 1 && Integer.parseInt(e.getStatus()) != 4).map(OrderInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("yearOrderPrice", orderYearPrice);

        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        // 近十天内订单统计
        result.put("orderNumWithinDays", baseMapper.selectOrderNumWithinDays(null));
        // 近十天内支出统计
        result.put("orderPriceWithinDays", baseMapper.selectOrderPriceWithinDays(null));
        // 订单类别统计
        result.put("orderDrugType", baseMapper.selectOrderType());
        return result;
    }

    /**
     * 供应商统计数据
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeDataBySupplier(Integer userId) {
        // 获取供应商信息
        SupplierInfo supplierInfo = supplierInfoService.getById(userId);
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 本月订单数量
        List<OrderInfo> orderList = baseMapper.selectOrderByMonth();
        orderList = orderList.stream().filter(e -> e.getSupplierId().equals(supplierInfo.getId())).collect(Collectors.toList());
        result.put("monthOrderNum", CollectionUtil.isEmpty(orderList) ? 0 : orderList.size());
        BigDecimal orderPrice = orderList.stream().filter(e -> Integer.parseInt(e.getStatus()) >= 1 && Integer.parseInt(e.getStatus()) != 4).map(OrderInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 获取本月支出
        result.put("monthOrderPrice", orderPrice);

        // 本年订单数量
        List<OrderInfo> orderYearList = baseMapper.selectOrderByYear();
        orderYearList = orderYearList.stream().filter(e -> e.getSupplierId().equals(supplierInfo.getId())).collect(Collectors.toList());
        result.put("yearOrderNum", CollectionUtil.isEmpty(orderYearList) ? 0 : orderYearList.size());
        // 本年总支出
        BigDecimal orderYearPrice = orderYearList.stream().filter(e -> Integer.parseInt(e.getStatus()) >= 1 && Integer.parseInt(e.getStatus()) != 4).map(OrderInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("yearOrderPrice", orderYearPrice);

        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        // 近十天内订单统计
        result.put("orderNumWithinDays", baseMapper.selectOrderNumWithinDays(supplierInfo.getId()));
        // 近十天内支出统计
        result.put("orderPriceWithinDays", baseMapper.selectOrderPriceWithinDays(supplierInfo.getId()));
        return result;
    }
}
