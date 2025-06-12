package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IPurchaseQuotationInfoService extends IService<PurchaseQuotationInfo> {

    /**
     * 分页获取采购计划报价管理
     *
     * @param page      分页对象
     * @param queryFrom 采购计划报价管理
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseQuotationInfo> page, PurchaseQuotationInfo queryFrom);

    /**
     * 新增采购计划报价【采购计划下发】
     *
     * @param addFrom 采购计划报价管理对象
     * @return 结果
     */
    boolean purchasePlanIssue(PurchaseQuotationInfo addFrom) throws F1k2Exception;

    /**
     * 修改采购计划报价状态
     *
     * @param id     主键ID
     * @param status 采购计划报价状态
     * @return 结果
     */
    boolean setQuotationStatus(Integer id, String status);

    /**
     * 根据采购计划ID查询采购计划报价
     *
     * @param planId 采购计划ID
     * @return 采购计划报价列表
     */
    List<LinkedHashMap<String, Object>> queryQuotationByPlanId(Integer planId);
}
