package com.fank.f1k2.system.controller;

import com.fank.f1k2.common.annotation.Log;
import com.fank.f1k2.common.controller.BaseController;
import com.fank.f1k2.common.domain.QueryRequest;
import com.fank.f1k2.common.exception.F1k2Exception;
import com.fank.f1k2.system.domain.Dept;
import com.fank.f1k2.system.service.DeptService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("dept")
public class DeptController extends BaseController {

    private String message;

    @Autowired
    private DeptService deptService;

    @GetMapping
    public Map<String, Object> deptList(QueryRequest request, Dept dept) {
        return this.deptService.findDepts(request, dept);
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("dept:add")
    public void addDept(@Valid Dept dept) throws F1k2Exception {
        try {
            this.deptService.createDept(dept);
        } catch (Exception e) {
            message = "新增部门失败";
            log.error(message, e);
            throw new F1k2Exception(message);
        }
    }

    @Log("删除部门")
    @DeleteMapping("/{deptIds}")
    @RequiresPermissions("dept:delete")
    public void deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws F1k2Exception {
        try {
            String[] ids = deptIds.split(StringPool.COMMA);
            this.deptService.deleteDepts(ids);
        } catch (Exception e) {
            message = "删除部门失败";
            log.error(message, e);
            throw new F1k2Exception(message);
        }
    }

    @Log("修改部门")
    @PutMapping
    @RequiresPermissions("dept:update")
    public void updateDept(@Valid Dept dept) throws F1k2Exception {
        try {
            this.deptService.updateDept(dept);
        } catch (Exception e) {
            message = "修改部门失败";
            log.error(message, e);
            throw new F1k2Exception(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("dept:export")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws F1k2Exception {
        try {
            List<Dept> depts = this.deptService.findDepts(dept, request);
            ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new F1k2Exception(message);
        }
    }
}
