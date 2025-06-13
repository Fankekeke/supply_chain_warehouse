package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierMaterialsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface SupplierMaterialsInfoMapper extends BaseMapper<SupplierMaterialsInfo> {

    /**
     * 分页获取供应商物料
     *
     * @param page      分页对象
     * @param queryFrom 供应商物料
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierMaterialsInfo> page, @Param("queryForm") SupplierMaterialsInfo queryFrom);

    /**
     * 根据供应商ID查询供应商物料
     *
     * @param supplierId 供应商ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryMaterialsBySupplierId(@Param("supplierId") Integer supplierId);
}
