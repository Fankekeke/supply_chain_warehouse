package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchasePlanInfo;
import com.fank.f1k2.business.dao.PurchasePlanInfoMapper;
import com.fank.f1k2.business.service.IPurchasePlanInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class PurchasePlanInfoServiceImpl extends ServiceImpl<PurchasePlanInfoMapper, PurchasePlanInfo> implements IPurchasePlanInfoService {

    /**
     * 分页获取采购计划
     *
     * @param page       分页对象
     * @param queryFrom 采购计划
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchasePlanInfo> page, PurchasePlanInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
