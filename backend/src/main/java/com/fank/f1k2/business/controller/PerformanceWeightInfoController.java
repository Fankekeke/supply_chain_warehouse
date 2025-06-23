package com.fank.f1k2.business.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PerformanceWeightInfo;
import com.fank.f1k2.business.service.IPerformanceWeightInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.web.bind.annotation.RestController;

/**
 * 绩效评价权重 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "绩效评价权重")
@RestController
@RequestMapping("/business/performance-weight-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PerformanceWeightInfoController {

    private final IPerformanceWeightInfoService performanceWeightInfoService;

    /**
     * 分页获取绩效评价权重
     *
     * @param page      分页对象
     * @param queryFrom 绩效评价权重
     * @return 结果
     */
    @ApiOperation(value = "分页查询绩效权重", notes = "根据分页和筛选条件获取绩效评价权重信息")
    @GetMapping("/page")
    public R page(Page<PerformanceWeightInfo> page, PerformanceWeightInfo queryFrom) {
        return R.ok(performanceWeightInfoService.queryPage(page, queryFrom));
    }

    /**
     * 查询绩效评价权重详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "绩效权重详情", notes = "通过ID获取绩效评价权重详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(performanceWeightInfoService.getById(id));
    }

    /**
     * 查询绩效评价权重列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取绩效权重列表", notes = "列出所有绩效评价权重信息")
    @GetMapping("/list")
    public R list() {
        return R.ok(performanceWeightInfoService.list());
    }

    /**
     * 新增绩效评价权重
     *
     * @param addFrom 绩效评价权重对象
     * @return 结果
     */
    @ApiOperation(value = "新增绩效权重", notes = "创建一个新的绩效评价权重记录")
    @PostMapping
    public R save(PerformanceWeightInfo addFrom) {
        return R.ok(performanceWeightInfoService.save(addFrom));
    }

    /**
     * 修改绩效评价权重
     *
     * @param editFrom 绩效评价权重对象
     * @return 结果
     */
    @ApiOperation(value = "修改绩效权重", notes = "更新已有的绩效权重配置并校验权重项合理性")
    @PutMapping
    public R edit(PerformanceWeightInfo editFrom) throws F1k2Exception {
        List<PerformanceWeightInfo> children = editFrom.getChildren();
        if (CollectionUtil.isEmpty(children)) {
            throw new F1k2Exception("请选择子项");
        }
        Set<String> seenCodes = new HashSet<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (PerformanceWeightInfo child : children) {
            String code = child.getCode();
            if (code == null) {
                throw new F1k2Exception("权重项编号为空");
            }
            if (seenCodes.contains(code)) {
                throw new F1k2Exception("权重项编号重复: " + code);
            }
            seenCodes.add(code);
            BigDecimal weightRate = child.getWeightRate();
            if (weightRate == null) {
                throw new F1k2Exception("权重比率为空");
            }
            sum = sum.add(weightRate);
        }
        if (sum.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new F1k2Exception("权重比率相加不等于100");
        }
        performanceWeightInfoService.remove(Wrappers.<PerformanceWeightInfo>lambdaQuery());
        return R.ok(performanceWeightInfoService.saveBatch(children));
    }

    /**
     * 删除绩效评价权重
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除绩效权重", notes = "根据ID集合批量删除绩效评价权重记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(performanceWeightInfoService.removeByIds(ids));
    }

}
