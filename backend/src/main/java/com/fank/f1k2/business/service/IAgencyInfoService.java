package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AgencyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IAgencyInfoService extends IService<AgencyInfo> {

    /**
     * 分页获取代办任务-供应商
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageBySupplier(Page<AgencyInfo> page, AgencyInfo queryFrom);

    /**
     * 分页获取代办任务-员工
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageByStaff(Page<AgencyInfo> page, AgencyInfo queryFrom);
}
