package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierAuditRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface ISupplierAuditRecordService extends IService<SupplierAuditRecord> {

    /**
     * 分页获取供应商审核记录
     *
     * @param page      分页对象
     * @param queryFrom 供应商审核记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierAuditRecord> page, SupplierAuditRecord queryFrom);

    /**
     * 供应商审核
     *
     * @param supplierAuditRecord 审核信息
     * @return 结果
     */
    boolean supplierAudit(SupplierAuditRecord supplierAuditRecord) throws Exception;
}
