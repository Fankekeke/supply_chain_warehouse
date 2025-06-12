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
        // 更新采购计划
        PurchasePlanInfo purchasePlanInfo = purchasePlanInfoMapper.selectById(addFrom.getPlanId());
        if (purchasePlanInfo != null) {
            purchasePlanInfo.setStatus("1");
            purchasePlanInfoMapper.updateById(purchasePlanInfo);
        }
        // TODO 采购计划状态更新发送消息或者代办

        // 保存
        return this.saveBatch(purchaseQuotationInfoList);
    }

    /**
     * 修改采购计划报价状态
     *
     * @param id     主键ID
     * @param status 采购计划报价状态
     * @return 结果
     */
    @Override
    public boolean setQuotationStatus(Integer id, String status) {
        // TODO 校验状态发送消息通知或代办

        return this.update(Wrappers.<PurchaseQuotationInfo>lambdaUpdate().set(PurchaseQuotationInfo::getStatus, status).eq(PurchaseQuotationInfo::getId, id));
    }

    /**
     * 根据采购计划ID查询采购计划报价
     *
     * @param planId 采购计划ID
     * @return 采购计划报价列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryQuotationByPlanId(Integer planId) {
        return baseMapper.queryQuotationByPlanId(planId);
    }
}
