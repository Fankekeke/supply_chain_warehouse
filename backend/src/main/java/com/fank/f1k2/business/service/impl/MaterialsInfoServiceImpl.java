package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.MaterialsInfo;
import com.fank.f1k2.business.dao.MaterialsInfoMapper;
import com.fank.f1k2.business.service.IMaterialsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class MaterialsInfoServiceImpl extends ServiceImpl<MaterialsInfoMapper, MaterialsInfo> implements IMaterialsInfoService {

    /**
     * 分页获取物料管理
     *
     * @param page       分页对象
     * @param queryFrom 物料管理
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<MaterialsInfo> page, MaterialsInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
