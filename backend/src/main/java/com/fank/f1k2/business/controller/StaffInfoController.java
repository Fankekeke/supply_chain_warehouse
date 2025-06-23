package com.fank.f1k2.business.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.StaffInfo;
import com.fank.f1k2.business.service.IStaffInfoService;
import com.fank.f1k2.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 员工管理 控制层
 *
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Api(tags = "员工管理")
@RestController
@RequestMapping("/business/staff-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffInfoController {

    private final IStaffInfoService staffInfoService;

    private final UserService userService;

    /**
     * 分页获取员工管理
     *
     * @param page      分页对象
     * @param queryFrom 员工管理
     * @return 结果
     */
    @ApiOperation(value = "分页查询员工", notes = "根据分页和筛选条件获取员工信息")
    @GetMapping("/page")
    public R page(Page<StaffInfo> page, StaffInfo queryFrom) {
        return R.ok(staffInfoService.queryPage(page, queryFrom));
    }

    /**
     * 根据系统用户ID查询员工信息
     *
     * @param sysUserId 系统用户ID
     * @return 员工信息
     */
    @ApiOperation(value = "按用户ID查员工", notes = "通过系统用户ID获取对应的员工信息")
    @GetMapping("/queryStaffBySysUserId")
    public R queryStaffBySysUserId(Integer sysUserId) {
        return R.ok(staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery()
                .eq(StaffInfo::getSysUserId, sysUserId)));
    }

    /**
     * 查询员工列表
     *
     * @param queryFrom 员工管理
     * @return 列表
     */
    @ApiOperation(value = "查询员工列表", notes = "根据条件列出当前所有员工信息")
    @GetMapping("/queryStaffList")
    public R queryStaffList(StaffInfo queryFrom) {
        return R.ok(staffInfoService.queryStaffList(queryFrom));
    }

    /**
     * 查询员工管理详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @ApiOperation(value = "员工详情", notes = "通过ID获取员工详细信息")
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(staffInfoService.getById(id));
    }

    /**
     * 查询员工管理列表
     *
     * @return 结果
     */
    @ApiOperation(value = "获取员工集合", notes = "列出所有员工记录")
    @GetMapping("/list")
    public R list() {
        return R.ok(staffInfoService.list());
    }

    /**
     * 新增员工管理
     *
     * @param addFrom 员工管理对象
     * @return 结果
     */
    @ApiOperation(value = "新增员工", notes = "创建一个新的员工并注册关联的系统用户")
    @PostMapping
    public R save(StaffInfo addFrom) throws Exception {
        addFrom.setCode("SAF-" + System.currentTimeMillis());
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        userService.registerStaff(addFrom);
        return R.ok(true);
    }

    /**
     * 修改员工管理
     *
     * @param editFrom 员工管理对象
     * @return 结果
     */
    @ApiOperation(value = "修改员工信息", notes = "更新已有的员工信息")
    @PutMapping
    public R edit(StaffInfo editFrom) {
        return R.ok(staffInfoService.updateById(editFrom));
    }

    /**
     * 删除员工管理
     *
     * @param ids 删除的主键ID
     * @return 结果
     */
    @ApiOperation(value = "删除员工", notes = "根据ID集合批量删除员工记录")
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(staffInfoService.removeByIds(ids));
    }
}
