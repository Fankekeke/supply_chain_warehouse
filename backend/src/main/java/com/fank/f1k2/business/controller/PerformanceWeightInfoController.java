package com.fank.f1k2.business.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.PerformanceWeightInfo;
import com.fank.f1k2.business.service.IPerformanceWeightInfoService;
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
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(performanceWeightInfoService.getById(id));
    }

    /**
     * 查询绩效评价权重列表
     *
     * @return 结果
     */
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
    @PostMapping
    public R save(@RequestBody PerformanceWeightInfo addFrom) {
        return R.ok(performanceWeightInfoService.save(addFrom));
    }

    /**
     * 修改绩效评价权重
     *
     * @param editFrom 绩效评价权重对象
     * @return 结果
     */
    @PutMapping
    public R edit(@RequestBody PerformanceWeightInfo editFrom) throws F1k2Exception {
        List<PerformanceWeightInfo> children = editFrom.getChildren();
        if (CollectionUtil.isEmpty(children)) {
            throw new F1k2Exception("请选择子项");
        }
        // 校验权重项编号是否重复
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
        // 校验权重比率相加是否等于100
        if (sum.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new F1k2Exception("权重比率相加不等于100");
        }
        // 删除旧数据
        performanceWeightInfoService.remove(Wrappers.<PerformanceWeightInfo>lambdaQuery());
        return R.ok(performanceWeightInfoService.saveBatch(children));
    }

    /**
     * 删除绩效评价权重
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(performanceWeightInfoService.removeByIds(ids));
    }

}
