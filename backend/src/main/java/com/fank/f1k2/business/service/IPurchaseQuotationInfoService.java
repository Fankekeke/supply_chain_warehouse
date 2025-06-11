package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;

import java.util.LinkedHashMap;

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
}
