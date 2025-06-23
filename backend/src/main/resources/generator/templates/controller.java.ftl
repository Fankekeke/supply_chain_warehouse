package ${package.Controller};


import cn.hutool.core.date.DateUtil;
import com.fank.f1k2.common.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.${entity};
import com.fank.f1k2.business.service.${table.serviceName};
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!} 控制层
 * @author ${author}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    private final ${table.serviceName} bulletinInfoService;

    /**
    * 分页获取${table.comment!}
    *
    * @param page       分页对象
    * @param queryFrom ${table.comment!}
    * @return 结果
    */
    @GetMapping("/page")
    public R page(Page<${entity}> page, ${entity} queryFrom) {
        return R.ok();
    }

    /**
    * 查询${table.comment!}详情
    *
    * @param id 主键ID
    * @return 结果
    */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    /**
    * 查询${table.comment!}列表
    *
    * @return 结果
    */
    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
    * 新增${table.comment!}
    *
    * @param addFrom ${table.comment!}对象
    * @return 结果
    */
    @PostMapping
    public R save(${entity} addFrom) {
        addFrom.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(addFrom));
    }

    /**
    * 修改${table.comment!}
    *
    * @param editFrom ${table.comment!}对象
    * @return 结果
    */
    @PutMapping
    public R edit(${entity} editFrom) {
        return R.ok(bulletinInfoService.updateById(editFrom));
    }

    /**
    * 删除${table.comment!}
    *
    * @param ids 删除的主键ID
    * @return 结果
    */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }

}
</#if>
