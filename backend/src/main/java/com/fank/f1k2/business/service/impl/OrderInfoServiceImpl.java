package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.*;
import com.fank.f1k2.business.dao.OrderInfoMapper;
import com.fank.f1k2.business.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.*;
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

    private final IWarehouseInfoService warehouseInfoService;

    private final IWarehousePutRecordService warehousePutRecordService;

    private final IMailService mailService;

    private final TemplateEngine templateEngine;

    private final INotifyInfoService notifyInfoService;

    private final IAgencyInfoService agencyInfoService;

    private final IMaterialsInfoService materialsInfoService;

    private final IPurchasePlanInfoService purchasePlanInfoService;

    private final IPurchaseNeedInfoService purchaseNeedInfoService;

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
     * 供应商订单数据
     *
     * @param supplierId 供应商ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryListBySupplier(Integer supplierId) {
        return baseMapper.queryListBySupplier(supplierId);
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
        OrderInfo orderInfo = this.getById(id);
        orderInfo.setId(id);
        orderInfo.setStatus(status);
        // TODO 采购状态更新发送消息或者代办
        // 获取供应商信息
        SupplierInfo supplierInfo = supplierInfoService.getById(orderInfo.getSupplierId());

        if ("4".equals(orderInfo.getStatus())) {
            NotifyInfo messageInfo = new NotifyInfo();
            messageInfo.setAgencyType("1");
            messageInfo.setUserId(supplierInfo.getId());
            messageInfo.setStatus("0");
            String content = "您好, 企业：" + supplierInfo.getName() + " 采购订单：" + orderInfo.getCode() + " 已被退货，请前往查看";
            messageInfo.setContent(content);
            messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            notifyInfoService.save(messageInfo);
            if (StrUtil.isNotEmpty(supplierInfo.getEmail())) {
                Context context = new Context();
                context.setVariable("today", DateUtil.formatDate(new Date()));
                context.setVariable("custom", content);
                String emailContent = templateEngine.process("registerEmail", context);
                mailService.sendHtmlMail(supplierInfo.getEmail(), DateUtil.formatDate(new Date()) + "采购订单提示", emailContent);
            }
        }
        NotifyInfo messageInfo = new NotifyInfo();
        messageInfo.setAgencyType("2");
        messageInfo.setStatus("0");
        // 获取当前状态
        String statusValue = "";
        switch (orderInfo.getStatus()) {
            case "1":
                statusValue = "待审核";
                break;
            case "2":
                statusValue = "待报价";
                break;
            case "3":
                statusValue = "待审核";
                break;
            case "4":
                statusValue = "退货";
                break;
            case "5":
                statusValue = "完成";
                break;
            default:
                statusValue = "待审核";
                break;
        }
        String content = "您好, 企业" + supplierInfo.getName() + " 采购订单：" + orderInfo.getCode() + " 状态已更新为：" + statusValue;
        messageInfo.setContent(content);
        messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        notifyInfoService.save(messageInfo);

        Context context = new Context();
        context.setVariable("today", DateUtil.formatDate(new Date()));
        context.setVariable("custom", content);
        String emailContent = templateEngine.process("registerEmail", context);
        mailService.sendHtmlMail("fan1ke2ke@gmail.com", DateUtil.formatDate(new Date()) + "采购订单提示", emailContent);
        // 更新采购订单状态
        return updateById(orderInfo);
    }

    /**
     * 采购订单入库
     *
     * @param orderId 订单编号
     * @return 结果
     */
    @Override
    public boolean orderPutStock(Integer orderId) {
        // 获取订单信息
        OrderInfo orderInfo = this.getById(orderId);

        // 获取物料信息
        MaterialsInfo materialsInfo = materialsInfoService.getById(orderInfo.getMaterialsCode());
        String materialsName = materialsInfo == null ? "" : materialsInfo.getName();

        // 获取库存信息
        List<WarehouseInfo> warehouseInfoList = warehouseInfoService.list(Wrappers.<WarehouseInfo>lambdaQuery().eq(WarehouseInfo::getTransactionType, 0));
        Map<String, WarehouseInfo> warehouseInfoMap = warehouseInfoList.stream().collect(Collectors.toMap(WarehouseInfo::getCode, e -> e));

        // 入库记录
        WarehousePutRecord warehousePutRecord = new WarehousePutRecord();
        warehousePutRecord.setCode("PUT-" + System.currentTimeMillis());
        warehousePutRecord.setName(warehousePutRecord.getCode() + "采购入库");
        warehousePutRecord.setTotalPrice(orderInfo.getTotalPrice());
        warehousePutRecord.setPutUser(orderInfo.getAuditUser());
        warehousePutRecord.setRemark(orderInfo.getContent());
        warehousePutRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        warehousePutRecordService.save(warehousePutRecord);

        WarehouseInfo currentStock = warehouseInfoMap.get(orderInfo.getMaterialsCode());
        if (currentStock == null) {
            currentStock = new WarehouseInfo();
            currentStock.setCode(orderInfo.getMaterialsCode());
            currentStock.setName(materialsName);
            currentStock.setQuantity(BigDecimal.valueOf(orderInfo.getPurchaseNum()));
            currentStock.setTransactionType(0);
            currentStock.setCreateDate(DateUtil.formatDateTime(new Date()));
            currentStock.setUnitPrice(NumberUtil.div(orderInfo.getTotalPrice(), orderInfo.getPurchaseNum()));
            warehouseInfoService.save(currentStock);
        } else {
            currentStock.setQuantity(NumberUtil.add(currentStock.getQuantity(), BigDecimal.valueOf(orderInfo.getPurchaseNum())));
            currentStock.setCreateDate(DateUtil.formatDateTime(new Date()));
            currentStock.setUnitPrice(NumberUtil.div(orderInfo.getTotalPrice(), orderInfo.getPurchaseNum()));
            warehouseInfoService.updateById(currentStock);
        }

        PurchasePlanInfo purchasePlanInfo = purchasePlanInfoService.getById(orderInfo.getPlanId());
        purchasePlanInfo.setStatus("5");
        purchasePlanInfoService.updateById(purchasePlanInfo);

        // 校验采购需求状态
        this.setNeedStatus(purchasePlanInfo.getPurchaseCode());

        WarehouseInfo putStock = new WarehouseInfo();
        putStock.setCode(orderInfo.getMaterialsCode());
        putStock.setName(materialsName);
        putStock.setQuantity(BigDecimal.valueOf(orderInfo.getPurchaseNum()));
        putStock.setTransactionType(1);
        putStock.setCreateDate(DateUtil.formatDateTime(new Date()));
        putStock.setInboundOrderNumber(warehousePutRecord.getCode());
        putStock.setUnitPrice(NumberUtil.div(orderInfo.getTotalPrice(), orderInfo.getPurchaseNum()));
        return warehouseInfoService.save(putStock);
    }

    /**
     * 设置采购需求状态
     *
     * @param purchaseCode 采购需求编号
     */
    public void setNeedStatus(String purchaseCode) {
        // 获取采购需求
        PurchaseNeedInfo purchaseNeedInfo = purchaseNeedInfoService.getOne(Wrappers.<PurchaseNeedInfo>lambdaQuery().eq(PurchaseNeedInfo::getCode, purchaseCode));
        // 根据采购需求编号获取采购计划
        List<PurchasePlanInfo> purchasePlanInfoList = purchasePlanInfoService.list(Wrappers.<PurchasePlanInfo>lambdaQuery().eq(PurchasePlanInfo::getPurchaseCode, purchaseCode));
        // 校验采购计划是否全部完成
        if (purchasePlanInfoList.stream().allMatch(e -> "5".equals(e.getStatus()))) {
            purchaseNeedInfo.setStatus("1");
            purchaseNeedInfoService.updateById(purchaseNeedInfo);
        }
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
        SupplierInfo supplierInfo = supplierInfoService.getOne(Wrappers.<SupplierInfo>lambdaQuery().eq(SupplierInfo::getSysUserId, userId));
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 本月订单数量
        List<OrderInfo> orderList = baseMapper.selectOrderByMonth();
        orderList = orderList.stream().filter(e -> e.getSupplierId().equals(supplierInfo.getId())).collect(Collectors.toList());
        result.put("monthOrderNum", CollectionUtil.isEmpty(orderList) ? 0 : orderList.size());
        BigDecimal orderPrice = orderList.stream().filter(e -> Integer.parseInt(e.getStatus()) >= 1 && Integer.parseInt(e.getStatus()) != 4).map(OrderInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 获取本月收入
        result.put("monthOrderPrice", orderPrice);

        // 本年订单数量
        List<OrderInfo> orderYearList = baseMapper.selectOrderByYear();
        orderYearList = orderYearList.stream().filter(e -> e.getSupplierId().equals(supplierInfo.getId())).collect(Collectors.toList());
        result.put("yearOrderNum", CollectionUtil.isEmpty(orderYearList) ? 0 : orderYearList.size());
        // 本年总收入
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
