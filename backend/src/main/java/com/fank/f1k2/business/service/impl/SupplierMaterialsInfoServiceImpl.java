package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierMaterialsInfo;
import com.fank.f1k2.business.dao.SupplierMaterialsInfoMapper;
import com.fank.f1k2.business.service.ISupplierMaterialsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class SupplierMaterialsInfoServiceImpl extends ServiceImpl<SupplierMaterialsInfoMapper, SupplierMaterialsInfo> implements ISupplierMaterialsInfoService {

    /**
     * 分页获取供应商物料
     *
     * @param page      分页对象
     * @param queryFrom 供应商物料
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierMaterialsInfo> page, SupplierMaterialsInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 根据供应商ID查询供应商物料
     *
     * @param supplierId 供应商ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryMaterialsBySupplierId(Integer supplierId) {
        return baseMapper.queryMaterialsBySupplierId(supplierId);
    }
}
