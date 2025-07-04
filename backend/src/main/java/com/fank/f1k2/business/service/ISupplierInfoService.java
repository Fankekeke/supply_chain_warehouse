package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface ISupplierInfoService extends IService<SupplierInfo> {

    /**
     * 分页获取供应商信息
     *
     * @param page      分页对象
     * @param queryFrom 供应商信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<SupplierInfo> page, SupplierInfo queryFrom);

    /**
     * 查询匹配的供应商信息
     *
     * @param materialsCode 匹配码
     * @return 结果
     */
    List<SupplierInfo> querySupplierByMatch(String materialsCode);

    /**
     * 根据系统用户ID查询供应商
     *
     * @param sysUserId 系统用户ID
     * @return 供应商列表
     */
    LinkedHashMap<String, Object> querySupplierBySysUserId(Integer sysUserId);
}
