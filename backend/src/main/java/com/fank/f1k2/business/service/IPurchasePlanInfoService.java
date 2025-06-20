package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchasePlanInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IPurchasePlanInfoService extends IService<PurchasePlanInfo> {

    /**
     * 分页获取采购计划
     *
     * @param page      分页对象
     * @param queryFrom 采购计划
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchasePlanInfo> page, PurchasePlanInfo queryFrom);

    /**
     * 根据采购编码获取采购计划
     *
     * @param purchaseCode 采购编码
     * @return 采购计划
     */
    List<LinkedHashMap<String, Object>> queryPlanByPurchaseCode(String purchaseCode);
}
