package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehousePutRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IWarehousePutRecordService extends IService<WarehousePutRecord> {

    /**
     * 分页获取库存入库记录
     *
     * @param page      分页对象
     * @param queryFrom 库存入库记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<WarehousePutRecord> page, WarehousePutRecord queryFrom);
}
