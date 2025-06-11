package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.dao.PurchasePlanInfoMapper;
import com.fank.f1k2.business.entity.PurchasePlanInfo;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.fank.f1k2.business.dao.PurchaseQuotationInfoMapper;
import com.fank.f1k2.business.service.IPurchaseQuotationInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.common.exception.F1k2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseQuotationInfoServiceImpl extends ServiceImpl<PurchaseQuotationInfoMapper, PurchaseQuotationInfo> implements IPurchaseQuotationInfoService {

    private final PurchasePlanInfoMapper purchasePlanInfoMapper;

    /**
     * 分页获取采购计划报价管理
     *
     * @param page      分页对象
     * @param queryFrom 采购计划报价管理
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseQuotationInfo> page, PurchaseQuotationInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 新增采购计划报价【采购计划下发】
     *
     * @param addFrom 采购计划报价管理对象
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean purchasePlanIssue(PurchaseQuotationInfo addFrom) throws F1k2Exception {
        List<PurchaseQuotationInfo> purchaseQuotationInfoList = addFrom.getPurchaseQuotationInfoList();
        if (CollectionUtil.isEmpty(purchaseQuotationInfoList)) {
            throw new F1k2Exception("采购计划报价管理对象为空");
        }

        for (PurchaseQuotationInfo purchaseQuotationInfo : purchaseQuotationInfoList) {
            purchaseQuotationInfo.setStatus("0");
            purchaseQuotationInfo.setCreateDate(addFrom.getCreateDate());
        }

        purchasePlanInfoMapper.update(Wrappers.<PurchasePlanInfo>lambdaUpdate().set(PurchasePlanInfo::getStatus, "1").eq(PurchasePlanInfo::getId, addFrom.getPlanId()));

        // 更新采购计划
        return this.saveBatch(purchaseQuotationInfoList);
    }
}
