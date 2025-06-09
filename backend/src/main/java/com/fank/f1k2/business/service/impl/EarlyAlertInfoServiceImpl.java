package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EarlyAlertInfo;
import com.fank.f1k2.business.dao.EarlyAlertInfoMapper;
import com.fank.f1k2.business.service.IEarlyAlertInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class EarlyAlertInfoServiceImpl extends ServiceImpl<EarlyAlertInfoMapper, EarlyAlertInfo> implements IEarlyAlertInfoService {

    /**
     * 分页获取预警库存设置
     *
     * @param page       分页对象
     * @param queryFrom 预警库存设置
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<EarlyAlertInfo> page, EarlyAlertInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
