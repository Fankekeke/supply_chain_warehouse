package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AgencyInfo;
import com.fank.f1k2.business.dao.AgencyInfoMapper;
import com.fank.f1k2.business.service.IAgencyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class AgencyInfoServiceImpl extends ServiceImpl<AgencyInfoMapper, AgencyInfo> implements IAgencyInfoService {

    /**
     * 分页获取代办任务-供应商
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPageBySupplier(Page<AgencyInfo> page, AgencyInfo queryFrom) {
        return baseMapper.queryPageBySupplier(page, queryFrom);
    }

    /**
     * 分页获取代办任务-员工
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPageByStaff(Page<AgencyInfo> page, AgencyInfo queryFrom) {
        return baseMapper.queryPageByStaff(page, queryFrom);
    }
}
