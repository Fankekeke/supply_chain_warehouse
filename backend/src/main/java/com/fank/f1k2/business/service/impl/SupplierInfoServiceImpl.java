package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.dao.SupplierInfoMapper;
import com.fank.f1k2.business.entity.SupplierMaterialsInfo;
import com.fank.f1k2.business.service.ISupplierInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.business.service.ISupplierMaterialsInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements ISupplierInfoService {

    private final ISupplierMaterialsInfoService supplierMaterialsInfoService;

    /**
     * 分页获取供应商信息
     *
     * @param page      分页对象
     * @param queryFrom 供应商信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierInfo> page, SupplierInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 查询匹配的供应商信息
     *
     * @param materialsCode 匹配码
     * @return 结果
     */
    @Override
    public List<SupplierInfo> querySupplierByMatch(String materialsCode) {
        // 获取可以供应的供应商信息
        List<SupplierMaterialsInfo> supplierMaterialsInfos = supplierMaterialsInfoService.list(Wrappers.<SupplierMaterialsInfo>lambdaQuery().eq(SupplierMaterialsInfo::getCode, materialsCode));
        if (CollectionUtil.isEmpty(supplierMaterialsInfos)) {
            return Collections.emptyList();
        }
        // 取出供应商ID
        List<Integer> supplierIds = supplierMaterialsInfos.stream().map(SupplierMaterialsInfo::getSupplierId).collect(Collectors.toList());
        return new ArrayList<>(this.listByIds(supplierIds));
    }
}
