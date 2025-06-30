package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.business.entity.MaterialsInfo;
import com.fank.f1k2.business.service.IMaterialsInfoService;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.EarlyAlertInfo;
import com.fank.f1k2.business.service.IEarlyAlertInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 预警库存设置 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "预警库存设置")
@RestController
@RequestMapping("/business/early-alert-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EarlyAlertInfoController {

    private final IEarlyAlertInfoService earlyAlertInfoService;

    private final IMaterialsInfoService materialsInfoService;

    /**
     * 分页获取预警库存设置
     *
     * @param page      分页对象
     * @param queryFrom 预警库存设置
     * @return 结果
     */
    @ApiOperation(value = "分页查询预警库存", notes = "根据分页和筛选条件获取预警库存设置")
    @GetMapping("/page")
    public R page(Page<EarlyAlertInfo> page, EarlyAlertInfo queryFrom) {
        return R.ok(earlyAlertInfoService.queryPage(page, queryFrom));
    }

    /**
     * 批量设置预警库存
     *
     * @return 批量设置结果
     */
    @ApiOperation(value = "批量设置预警库存", notes = "批量设置预警库存信息")
    @GetMapping("/setEarlyAlertInfoBatch")
    public R setEarlyAlertInfoBatch() {
        List<MaterialsInfo> materialsInfoList = materialsInfoService.list();
        List<EarlyAlertInfo> addList = new ArrayList<>();
        for (MaterialsInfo materialsInfo : materialsInfoList) {
            EarlyAlertInfo earlyAlertInfo = new EarlyAlertInfo();
            earlyAlertInfo.setMaterialsCode(materialsInfo.getCode());
            earlyAlertInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            earlyAlertInfo.setMinValue(-1);
            addList.add(earlyAlertInfo);
        }
        return R.ok(earlyAlertInfoService.saveBatch(addList));
    }

    /**
     * 查询预警库存设置详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "预警库存详情", notes = "通过ID获取预警库存详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(earlyAlertInfoService.getById(id));
    }

    /**
     * 查询预警库存设置列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取预警库存列表", notes = "列出所有预警库存设置信息")
    @GetMapping("/list")
    public R list() {
        return R.ok(earlyAlertInfoService.list());
    }

    /**
     * 新增预警库存设置
     *
     * @param addFrom 预警库存设置对象
     * @return 结果
     */
    @ApiOperation(value = "新增预警库存", notes = "创建一个新的预警库存设置")
    @PostMapping
    public R save(EarlyAlertInfo addFrom) throws F1k2Exception {
        int count = earlyAlertInfoService.count(Wrappers.<EarlyAlertInfo>lambdaQuery().eq(EarlyAlertInfo::getMaterialsCode, addFrom.getMaterialsCode()));
        if (count > 0) {
            throw new F1k2Exception("此物料已添加库存设置");
        }
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(earlyAlertInfoService.save(addFrom));
    }

    /**
     * 修改预警库存设置
     *
     * @param editFrom 预警库存设置对象
     * @return 结果
     */
    @ApiOperation(value = "修改预警库存", notes = "根据传入的预警库存信息更新已有的设置")
    @PutMapping
    public R edit(EarlyAlertInfo editFrom) {
        return R.ok(earlyAlertInfoService.updateById(editFrom));
    }

    /**
     * 删除预警库存设置
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除预警库存", notes = "根据ID集合批量删除预警库存设置")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(earlyAlertInfoService.removeByIds(ids));
    }

}
