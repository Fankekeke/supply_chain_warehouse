package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.dao.PurchasePlanInfoMapper;
import com.fank.f1k2.business.entity.NotifyInfo;
import com.fank.f1k2.business.entity.PurchasePlanInfo;
import com.fank.f1k2.business.entity.PurchaseQuotationInfo;
import com.fank.f1k2.business.dao.PurchaseQuotationInfoMapper;
import com.fank.f1k2.business.entity.SupplierInfo;
import com.fank.f1k2.business.entity.vo.ChatVo;
import com.fank.f1k2.business.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.common.exception.F1k2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseQuotationInfoServiceImpl extends ServiceImpl<PurchaseQuotationInfoMapper, PurchaseQuotationInfo> implements IPurchaseQuotationInfoService {

    private final PurchasePlanInfoMapper purchasePlanInfoMapper;

    private final IMailService mailService;

    private final TemplateEngine templateEngine;

    private final INotifyInfoService notifyInfoService;

    private final IAgencyInfoService agencyInfoService;

    private final ISupplierInfoService supplierInfoService;

    /**
     * 分页获取采购计划报价管理
     *
     * @param page      分页对象
     * @param queryFrom 采购计划报价管理
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<PurchaseQuotationInfo> page, PurchaseQuotationInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 新增采购计划报价【采购计划下发】
     *
     * @param addFrom 采购计划报价管理对象
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean purchasePlanIssue(PurchaseQuotationInfo addFrom) throws F1k2Exception {
        List<PurchaseQuotationInfo> purchaseQuotationInfoList = JSONUtil.toList(addFrom.getPurchaseQuotationInfoList(), PurchaseQuotationInfo.class);
        if (CollectionUtil.isEmpty(purchaseQuotationInfoList)) {
            throw new F1k2Exception("采购计划报价管理对象为空");
        }

        for (PurchaseQuotationInfo purchaseQuotationInfo : purchaseQuotationInfoList) {
            purchaseQuotationInfo.setStatus("0");
            purchaseQuotationInfo.setCreateDate(addFrom.getCreateDate());
        }
        // 更新采购计划
        PurchasePlanInfo purchasePlanInfo = purchasePlanInfoMapper.selectById(addFrom.getPlanId());
        if (purchasePlanInfo != null) {
            purchasePlanInfo.setStatus("1");
            purchasePlanInfoMapper.updateById(purchasePlanInfo);
        }
        // TODO 采购计划状态更新发送消息或者代办
        // 获取供应商信息
        List<Integer> supplierIdList = purchaseQuotationInfoList.stream().map(PurchaseQuotationInfo::getSupplierId).distinct().collect(Collectors.toList());
        List<SupplierInfo> supplierInfoList = new ArrayList<>(supplierInfoService.listByIds(supplierIdList));
        Map<Integer, SupplierInfo> supplierInfoMap = supplierInfoList.stream().collect(Collectors.toMap(SupplierInfo::getId, supplierInfo -> supplierInfo));
        for (PurchaseQuotationInfo purchaseQuotationInfo : purchaseQuotationInfoList) {
            purchaseQuotationInfo.setEndDate(addFrom.getEndDate());
            purchaseQuotationInfo.setPlanId(addFrom.getPlanId());
            purchaseQuotationInfo.setMaterialsCode(addFrom.getMaterialsCode());
            SupplierInfo supplierInfo = supplierInfoMap.get(purchaseQuotationInfo.getSupplierId());
            NotifyInfo messageInfo = new NotifyInfo();
            messageInfo.setAgencyType("1");
            messageInfo.setStatus("0");
            messageInfo.setUserId(supplierInfo.getId());
            String content = "您好, 企业：" + supplierInfo.getName() + " 采购计划：" + purchasePlanInfo.getCode() + " 已提交报价，请前往接收";
            messageInfo.setContent(content);
            messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            notifyInfoService.save(messageInfo);
            if (StrUtil.isNotEmpty(supplierInfo.getEmail())) {
                Context context = new Context();
                context.setVariable("today", DateUtil.formatDate(new Date()));
                context.setVariable("custom", content);
                String emailContent = templateEngine.process("registerEmail", context);
                mailService.sendHtmlMail(supplierInfo.getEmail(), DateUtil.formatDate(new Date()) + "报价接收", emailContent);
            }
        }

        // 保存
        return this.saveBatch(purchaseQuotationInfoList);
    }

    /**
     * 修改采购计划报价状态
     *
     * @param id     主键ID
     * @param status 采购计划报价状态
     * @return 结果
     */
    @Override
    public boolean setQuotationStatus(Integer id, String status) {
        // 获取供应商信息
        PurchaseQuotationInfo purchaseQuotationInfo = this.getById(id);
        SupplierInfo supplierInfo = supplierInfoService.getById(purchaseQuotationInfo.getSupplierId());
        // TODO 校验状态发送消息通知或代办
        // 1.已接收 2.已报价
        NotifyInfo messageInfo = new NotifyInfo();
        messageInfo.setAgencyType("2");
        messageInfo.setStatus("0");
        String content = "您好, 企业：" + supplierInfo.getName() + " 采购计划：" + purchaseQuotationInfo.getPlanCode() + " 已" + (status.equals("1") ? "接收" : "报价") + "，请前往查看";
        messageInfo.setContent(content);
        messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        notifyInfoService.save(messageInfo);

        Context context = new Context();
        context.setVariable("today", DateUtil.formatDate(new Date()));
        context.setVariable("custom", content);
        String emailContent = templateEngine.process("registerEmail", context);
        mailService.sendHtmlMail("fan1ke2ke@gmail.com", DateUtil.formatDate(new Date()) + "采购报价提示", emailContent);
        return this.update(Wrappers.<PurchaseQuotationInfo>lambdaUpdate().set(PurchaseQuotationInfo::getStatus, status).eq(PurchaseQuotationInfo::getId, id));
    }

    /**
     * 根据采购计划ID查询采购计划报价
     *
     * @param planId 采购计划ID
     * @return 采购计划报价列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryQuotationByPlanId(Integer planId) {
        return baseMapper.queryQuotationByPlanId(planId);
    }

    /**
     * 查询历史报价
     *
     * @param materialCode 物料编码
     * @return 采购计划报价列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryHistoryQuotation(String materialCode) {
        return baseMapper.queryHistoryQuotation(materialCode);
    }

    /**
     * 回复
     *
     * @param content 回复内容
     * @param quotationId 报价ID
     * @param type 回复类型
     * @return 结果
     */
    @Override
    public boolean reply(String content, Integer quotationId, String type) {
        // 获取采购计划报价信息
        PurchaseQuotationInfo purchaseQuotationInfo = this.getById(quotationId);
        ChatVo chatVo = new ChatVo();
        chatVo.setContent(content);
        chatVo.setCreateDate(DateUtil.formatDateTime(new Date()));
        chatVo.setType(type);
        if (StrUtil.isEmpty(purchaseQuotationInfo.getChatContent())) {
            String chatVoList = JSONUtil.toJsonStr(Collections.singletonList(chatVo));
            purchaseQuotationInfo.setChatContent(chatVoList);
            return this.updateById(purchaseQuotationInfo);
        } else {
            List<ChatVo> chatVoList = JSONUtil.toList(purchaseQuotationInfo.getChatContent(), ChatVo.class);
            chatVoList.add(chatVo);
            purchaseQuotationInfo.setChatContent(JSONUtil.toJsonStr(chatVoList));
            return this.updateById(purchaseQuotationInfo);
        }
    }

    /**
     * 根据报价ID查询回复
     *
     * @param quotationId 报价ID
     * @return 回复列表
     */
    @Override
    public List<ChatVo> queryReplyByQuotationId(Integer quotationId) {
        // 获取采购计划报价信息
        PurchaseQuotationInfo purchaseQuotationInfo = this.getById(quotationId);
        if (StrUtil.isEmpty(purchaseQuotationInfo.getChatContent())) {
            return Collections.emptyList();
        }
        return JSONUtil.toList(purchaseQuotationInfo.getChatContent(), ChatVo.class);
    }
}
