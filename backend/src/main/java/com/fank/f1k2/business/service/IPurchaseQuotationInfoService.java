package com.fank.f1k2.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fank.f1k2.business.entity.vo.ChatVo;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface IPurchaseQuotationInfoService extends IService<PurchaseQuotationInfo> {

    /**
     * 分页获取采购计划报价管理
     *
     * @param page      分页对象
     * @param queryFrom 采购计划报价管理
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseQuotationInfo> page, PurchaseQuotationInfo queryFrom);

    /**
     * 新增采购计划报价【采购计划下发】
     *
     * @param addFrom 采购计划报价管理对象
     * @return 结果
     */
    boolean purchasePlanIssue(PurchaseQuotationInfo addFrom) throws F1k2Exception;

    /**
     * 修改采购计划报价状态
     *
     * @param id     主键ID
     * @param status 采购计划报价状态
     * @return 结果
     */
    boolean setQuotationStatus(Integer id, String status);

    /**
     * 根据采购计划ID查询采购计划报价
     *
     * @param planId 采购计划ID
     * @return 采购计划报价列表
     */
    List<LinkedHashMap<String, Object>> queryQuotationByPlanId(Integer planId);

    /**
     * 查询历史报价
     *
     * @param materialCode 物料编码
     * @return 采购计划报价列表
     */
    List<LinkedHashMap<String, Object>> queryHistoryQuotation(String materialCode);

    /**
     * 回复
     *
     * @param content 回复内容
     * @param quotationId 报价ID
     * @param type 回复类型
     * @return 结果
     */
    boolean reply(String content, Integer quotationId, String type);

    /**
     * 根据报价ID查询回复
     *
     * @param quotationId 报价ID
     * @return 回复列表
     */
    List<ChatVo> queryReplyByQuotationId(Integer quotationId);
}
