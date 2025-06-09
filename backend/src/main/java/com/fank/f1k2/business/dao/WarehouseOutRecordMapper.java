package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehouseOutRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface WarehouseOutRecordMapper extends BaseMapper<WarehouseOutRecord> {

    /**
     * 分页获取库存出库记录
     *
     * @param page      分页对象
     * @param queryFrom 库存出库记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<WarehouseOutRecord> page, WarehouseOutRecord queryFrom);
}
