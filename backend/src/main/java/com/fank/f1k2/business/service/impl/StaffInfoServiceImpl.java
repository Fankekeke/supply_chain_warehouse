package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.StaffInfo;
import com.fank.f1k2.business.dao.StaffInfoMapper;
import com.fank.f1k2.business.service.IStaffInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo> implements IStaffInfoService {

    /**
     * 分页获取员工管理
     *
     * @param page      分页对象
     * @param queryFrom 员工管理
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<StaffInfo> page, StaffInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 查询员工列表
     *
     * @param queryFrom 员工管理
     * @return 列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryStaffList(StaffInfo queryFrom) {
        return baseMapper.queryStaffList(queryFrom);
    }
}
