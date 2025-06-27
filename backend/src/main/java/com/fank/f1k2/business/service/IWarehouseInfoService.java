package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehouseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IWarehouseInfoService extends IService<WarehouseInfo> {

    /**
     * 分页获取库房库存
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom);

    /**
     * 分页获取预警库存
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryAlertStockPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom);

    /**
     * 分页获取出入库详情
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryStockDetailPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom);

    /**
     * 获取预警库存列表
     *
     * @return 列表
     */
    List<LinkedHashMap<String, Object>> queryAlertStockList();

    /**
     * 查询入库库存记录
     *
     * @param code 入库编码
     * @return 列表
     */
    List<LinkedHashMap<String, Object>> queryPutRecordDetail(String code);

    /**
     * 查询出库库存记录
     *
     * @param code 出库编码
     * @return 列表
     */
    List<LinkedHashMap<String, Object>> queryOutRecordDetail(String code);
}
