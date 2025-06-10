package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AgencyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface AgencyInfoMapper extends BaseMapper<AgencyInfo> {

    /**
     * 分页获取代办任务-供应商
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageBySupplier(Page<AgencyInfo> page, @Param("queryForm") AgencyInfo queryFrom);

    /**
     * 分页获取代办任务-员工
     *
     * @param page      分页对象
     * @param queryFrom 代办任务
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageByStaff(Page<AgencyInfo> page, @Param("queryForm") AgencyInfo queryFrom);
}
