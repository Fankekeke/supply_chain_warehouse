package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.NotifyInfo;
import com.fank.f1k2.business.dao.NotifyInfoMapper;
import com.fank.f1k2.business.service.INotifyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class NotifyInfoServiceImpl extends ServiceImpl<NotifyInfoMapper, NotifyInfo> implements INotifyInfoService {

    /**
     * 分页获取消息通知-供应商
     *
     * @param page      分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPageBySupplier(Page<NotifyInfo> page, NotifyInfo queryFrom) {
        return baseMapper.queryPageBySupplier(page, queryFrom);
    }

    /**
     * 分页获取消息通知-员工
     *
     * @param page      分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPageByStaff(Page<NotifyInfo> page, NotifyInfo queryFrom) {
        return baseMapper.queryPageByStaff(page, queryFrom);
    }
}
