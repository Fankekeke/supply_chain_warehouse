package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.MaterialsInfo;
import com.fank.f1k2.business.entity.WarehouseInfo;
import com.fank.f1k2.business.dao.WarehouseInfoMapper;
import com.fank.f1k2.business.service.IMaterialsInfoService;
import com.fank.f1k2.business.service.IWarehouseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.common.exception.F1k2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoMapper, WarehouseInfo> implements IWarehouseInfoService {

    private final IMaterialsInfoService materialsInfoService;

    /**
     * 分页获取库房库存
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }

    /**
     * 分页获取预警库存
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryAlertStockPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom) {
        return baseMapper.queryAlertStockPage(page, queryFrom);
    }

    /**
     * 分页获取出入库详情
     *
     * @param page      分页对象
     * @param queryFrom 库房库存
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryStockDetailPage(Page<WarehouseInfo> page, WarehouseInfo queryFrom) {
        return baseMapper.queryStockDetailPage(page, queryFrom);
    }

    /**
     * 获取预警库存列表
     *
     * @return 列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryAlertStockList() {
        return baseMapper.queryAlertStockList();
    }

    /**
     * 查询入库库存记录
     *
     * @param code 入库编码
     * @return 列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryPutRecordDetail(String code) {
        return baseMapper.queryPutRecordDetail(code);
    }

    /**
     * 查询出库库存记录
     *
     * @param code 出库编码
     * @return 列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryOutRecordDetail(String code) {
        return baseMapper.queryOutRecordDetail(code);
    }

    /**
     * 导入信息列表
     *
     * @param file 文件
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public List<MaterialsInfo> importExcel(MultipartFile file) throws Exception {
        ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream(), 0);
        setExcelHeaderAlias(excelReader);
        List<MaterialsInfo> reportList = excelReader.read(1, 2, Integer.MAX_VALUE, MaterialsInfo.class);

        // 物料信息
        List<MaterialsInfo> typeList = materialsInfoService.list();
        Map<String, Integer> typeMap = typeList.stream().collect(Collectors.toMap(MaterialsInfo::getCode, MaterialsInfo::getId));

        if (CollectionUtil.isEmpty(reportList)) {
            throw new F1k2Exception("导入数据不得为空");
        }

        // 导入数据
        List<MaterialsInfo> reports = new ArrayList<>();
        for (MaterialsInfo report : reportList) {
            if (typeMap.get(report.getCode()) != null) {
                reports.add(report);
            }
        }
        return reports;
    }

    /**
     * 设置HeaderAlias
     *
     * @param excelReader HeaderAlias
     */
    public void setExcelHeaderAlias(ExcelReader excelReader) {
        excelReader.addHeaderAlias("物料编号", "code");
        excelReader.addHeaderAlias("物料名称", "name");
        excelReader.addHeaderAlias("数量", "minValue");
    }
}
