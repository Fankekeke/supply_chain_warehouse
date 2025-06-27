package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface PurchaseQuotationInfoMapper extends BaseMapper<PurchaseQuotationInfo> {

    /**
     * 分页获取采购计划报价管理
     *
     * @param page      分页对象
     * @param queryFrom 采购计划报价管理
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseQuotationInfo> page, @Param("queryForm") PurchaseQuotationInfo queryFrom);

    /**
     * 根据采购计划ID查询采购计划报价
     *
     * @param planId 采购计划ID
     * @return 采购计划报价列表
     */
    List<LinkedHashMap<String, Object>> queryQuotationByPlanId(@Param("planId") Integer planId);

    /**
     * 查询历史报价
     *
     * @param materialCode 物料编码
     * @return 采购计划报价列表
     */
    List<LinkedHashMap<String, Object>> queryHistoryQuotation(@Param("materialCode") String materialCode);
}
