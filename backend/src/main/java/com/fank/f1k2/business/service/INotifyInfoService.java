package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.NotifyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface INotifyInfoService extends IService<NotifyInfo> {

    /**
     * 分页获取消息通知-供应商
     *
     * @param page      分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageBySupplier(Page<NotifyInfo> page, NotifyInfo queryFrom);

    /**
     * 分页获取消息通知-员工
     *
     * @param page      分页对象
     * @param queryFrom 消息通知
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPageByStaff(Page<NotifyInfo> page, NotifyInfo queryFrom);
}
