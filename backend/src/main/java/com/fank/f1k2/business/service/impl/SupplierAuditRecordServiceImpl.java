package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierAuditRecord;
import com.fank.f1k2.business.dao.SupplierAuditRecordMapper;
import com.fank.f1k2.business.service.ISupplierAuditRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class SupplierAuditRecordServiceImpl extends ServiceImpl<SupplierAuditRecordMapper, SupplierAuditRecord> implements ISupplierAuditRecordService {

    /**
     * 分页获取供应商审核记录
     *
     * @param page      分页对象
     * @param queryFrom 供应商审核记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierAuditRecord> page, SupplierAuditRecord queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
