package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseNeedInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.common.exception.F1k2Exception;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IPurchaseNeedInfoService extends IService<PurchaseNeedInfo> {

    /**
     * 分页获取采购需求
     *
     * @param page       分页对象
     * @param queryFrom 采购需求
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseNeedInfo> page, PurchaseNeedInfo queryFrom);

    /**
     * 新增采购需求
     *
     * @param addFrom 采购需求对象
     * @return 结果
     */
    boolean addPurchaseNeed(PurchaseNeedInfo addFrom) throws F1k2Exception;
}
