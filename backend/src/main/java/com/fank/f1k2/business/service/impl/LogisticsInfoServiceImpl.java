package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.LogisticsInfo;
import com.fank.f1k2.business.dao.LogisticsInfoMapper;
import com.fank.f1k2.business.service.ILogisticsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class LogisticsInfoServiceImpl extends ServiceImpl<LogisticsInfoMapper, LogisticsInfo> implements ILogisticsInfoService {

    /**
     * 分页获取采购订单物流
     *
     * @param page       分页对象
     * @param queryFrom 采购订单物流
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<LogisticsInfo> page, LogisticsInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
