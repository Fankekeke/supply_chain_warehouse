package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseNeedInfo;
import com.fank.f1k2.business.dao.PurchaseNeedInfoMapper;
import com.fank.f1k2.business.service.IPurchaseNeedInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class PurchaseNeedInfoServiceImpl extends ServiceImpl<PurchaseNeedInfoMapper, PurchaseNeedInfo> implements IPurchaseNeedInfoService {

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
}
