package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseNeedInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface PurchaseNeedInfoMapper extends BaseMapper<PurchaseNeedInfo> {

    /**
     * 分页获取采购需求
     *
     * @param page      分页对象
     * @param queryFrom 采购需求
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseNeedInfo> page, @Param("queryForm") PurchaseNeedInfo queryFrom);
}
