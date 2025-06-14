package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.business.entity.BulletinInfo;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IBulletinInfoService extends IService<BulletinInfo> {

    /**
     * 分页获取公告信息
     * @param page 分页对象
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> getBulletinByPage(Page<BulletinInfo> page, BulletinInfo bulletinInfo);
}
