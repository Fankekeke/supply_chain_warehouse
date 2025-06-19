package com.fank.f1k2.business.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.dao.SupplierInfoMapper;
import com.fank.f1k2.business.entity.SupplierAuditRecord;
import com.fank.f1k2.business.dao.SupplierAuditRecordMapper;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.service.ISupplierAuditRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierAuditRecordServiceImpl extends ServiceImpl<SupplierAuditRecordMapper, SupplierAuditRecord> implements ISupplierAuditRecordService {

    private final SupplierInfoMapper supplierInfoMapper;

    private final UserService userService;

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

    /**
     * 供应商审核
     *
     * @param supplierAuditRecord 审核信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean supplierAudit(SupplierAuditRecord supplierAuditRecord) throws Exception {
        // 获取供应商信息
        SupplierInfo supplierInfo = supplierInfoMapper.selectById(supplierAuditRecord.getSupplierId());
        if ("1".equals(supplierAuditRecord.getStatus())) {
            // 设置供应商账户
            userService.registerSupplier(supplierInfo);
        }
        // TODO 发送消息通知

        supplierAuditRecord.setAuditDate(DateUtil.formatDateTime(new Date()));
        return this.updateById(supplierAuditRecord);
    }
}
