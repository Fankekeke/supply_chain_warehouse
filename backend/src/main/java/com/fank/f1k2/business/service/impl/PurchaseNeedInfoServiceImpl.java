package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.MaterialsInfo;
import com.fank.f1k2.business.entity.PurchaseNeedInfo;
import com.fank.f1k2.business.dao.PurchaseNeedInfoMapper;
import com.fank.f1k2.business.entity.PurchasePlanInfo;
import com.fank.f1k2.business.service.IPurchaseNeedInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.business.service.IPurchasePlanInfoService;
import com.fank.f1k2.common.exception.F1k2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseNeedInfoServiceImpl extends ServiceImpl<PurchaseNeedInfoMapper, PurchaseNeedInfo> implements IPurchaseNeedInfoService {

    private final IPurchasePlanInfoService purchasePlanInfoService;

    /**
     * 分页获取采购需求
     *
     * @param page       分页对象
     * @param queryFrom 采购需求
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseNeedInfo> page, PurchaseNeedInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 新增采购需求
     *
     * @param addFrom 采购需求对象
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPurchaseNeed(PurchaseNeedInfo addFrom) throws F1k2Exception {
        addFrom.setCode("PRN-" + System.currentTimeMillis());
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        addFrom.setStatus("0");
        // 添加采购计划
        List<MaterialsInfo> materialsInfoList = addFrom.getMaterialsInfoList();
        if (CollectionUtil.isEmpty(materialsInfoList)) {
            throw new F1k2Exception("请选择采购需求");
        }
        List<PurchasePlanInfo> addPlanInfoList = new ArrayList<>();
        for (MaterialsInfo materialsInfo : materialsInfoList) {
            PurchasePlanInfo planItemInfo = new PurchasePlanInfo();
            planItemInfo.setPurchaseCode(addFrom.getCode());
            planItemInfo.setCode(materialsInfo.getCode());
            planItemInfo.setPurchaseNum(materialsInfo.getMinValue());
            planItemInfo.setStatus("0");
            planItemInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            addPlanInfoList.add(planItemInfo);
        }
        purchasePlanInfoService.saveBatch(addPlanInfoList);
        return this.save(addFrom);
    }
}
