package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.WarehouseOutRecord;
import com.fank.f1k2.business.dao.WarehouseOutRecordMapper;
import com.fank.f1k2.business.service.IWarehouseOutRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class WarehouseOutRecordServiceImpl extends ServiceImpl<WarehouseOutRecordMapper, WarehouseOutRecord> implements IWarehouseOutRecordService {

    /**
     * 分页获取库存出库记录
     *
     * @param page      分页对象
     * @param queryFrom 库存出库记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<WarehouseOutRecord> page, WarehouseOutRecord queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
