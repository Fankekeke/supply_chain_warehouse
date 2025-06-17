package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PurchaseNeedInfo;
import com.fank.f1k2.business.service.IPurchaseNeedInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 采购需求 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "采购需求")
@RestController
@RequestMapping("/business/purchase-need-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseNeedInfoController {

    private final IPurchaseNeedInfoService purchaseNeedInfoService;

    /**
     * 分页获取采购需求
     *
     * @param page      分页对象
     * @param queryFrom 采购需求
     * @return 结果
     */
    @ApiOperation(value = "分页查询采购需求", notes = "根据分页和筛选条件获取采购需求信息")
    @GetMapping("/page")
    public R page(Page<PurchaseNeedInfo> page, PurchaseNeedInfo queryFrom) {
        return R.ok(purchaseNeedInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询采购需求详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "采购需求详情", notes = "通过ID获取采购需求详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(purchaseNeedInfoService.getById(id));
    }

    /**
     * 查询采购需求列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取采购需求列表", notes = "列出所有采购需求信息")
    @GetMapping("/list")
    public R list() {
        return R.ok(purchaseNeedInfoService.list());
    }

    /**
     * 新增采购需求
     *
     * @param addFrom 采购需求对象
     * @return 结果
     */
    @ApiOperation(value = "新增采购需求", notes = "创建一个新的采购需求记录")
    @PostMapping
    public R save(@RequestBody PurchaseNeedInfo addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(purchaseNeedInfoService.save(addFrom));
    }

    /**
     * 修改采购需求
     *
     * @param editFrom 采购需求对象
     * @return 结果
     */
    @ApiOperation(value = "修改采购需求", notes = "更新已有的采购需求信息")
    @PutMapping
    public R edit(@RequestBody PurchaseNeedInfo editFrom) {
        return R.ok(purchaseNeedInfoService.updateById(editFrom));
    }

    /**
     * 删除采购需求
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除采购需求", notes = "根据ID集合批量删除采购需求记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(purchaseNeedInfoService.removeByIds(ids));
    }

}
