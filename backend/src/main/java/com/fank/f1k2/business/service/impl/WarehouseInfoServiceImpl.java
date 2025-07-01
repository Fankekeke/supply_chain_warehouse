package com.fank.f1k2.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.MaterialsInfo;
import com.fank.f1k2.business.entity.WarehouseInfo;
import com.fank.f1k2.business.dao.WarehouseInfoMapper;
import com.fank.f1k2.business.entity.WarehouseOutRecord;
import com.fank.f1k2.business.service.IMaterialsInfoService;
import com.fank.f1k2.business.service.IWarehouseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fank.f1k2.business.service.IWarehouseOutRecordService;
import com.fank.f1k2.common.exception.F1k2Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoMapper, WarehouseInfo> implements IWarehouseInfoService {

    private final IMaterialsInfoService materialsInfoService;

    private final IWarehouseOutRecordService warehouseOutRecordService;

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
     * 出库
     *
     * @param warehouseOutRecord 出库记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean warehouseOut(WarehouseOutRecord warehouseOutRecord) throws F1k2Exception {
        warehouseOutRecord.setCode("OUT-" + System.currentTimeMillis());
        warehouseOutRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        List<WarehouseInfo> warehouseInfoList = JSONUtil.toList(warehouseOutRecord.getWarehouseInfoList(), WarehouseInfo.class);
        if (CollectionUtil.isEmpty(warehouseInfoList)) {
            throw new F1k2Exception("请选择出库信息");
        }
        // 根据物料编号获取库存记录
        List<String> codeList = warehouseInfoList.stream().map(WarehouseInfo::getCode).collect(Collectors.toList());
        List<WarehouseInfo> stockWarehouse = this.list(Wrappers.<WarehouseInfo>lambdaQuery().in(WarehouseInfo::getCode, codeList).eq(WarehouseInfo::getTransactionType, 0));
        Map<String, WarehouseInfo> warehouseInfoMap = stockWarehouse.stream().collect(Collectors.toMap(WarehouseInfo::getCode, e -> e));

        BigDecimal totalPrice = BigDecimal.ZERO;

        // 待更新的库存记录
        List<WarehouseInfo> updateList = new ArrayList<>();
        // 待添加的出库记录
        List<WarehouseInfo> addList = new ArrayList<>();

        for (WarehouseInfo warehouseInfo : warehouseInfoList) {
            // 获取库存信息
            WarehouseInfo warehouseInfoFromStock = warehouseInfoMap.get(warehouseInfo.getCode());
            warehouseInfoFromStock.setCreateDate(DateUtil.formatDateTime(new Date()));
            warehouseInfoFromStock.setQuantity(NumberUtil.sub(warehouseInfoFromStock.getQuantity(), warehouseInfo.getQuantity()));
            updateList.add(warehouseInfoFromStock);

            WarehouseInfo outWarehouseInfo = new WarehouseInfo();
            outWarehouseInfo.setCode(warehouseInfoFromStock.getCode());
            outWarehouseInfo.setName(warehouseInfoFromStock.getName());
            outWarehouseInfo.setQuantity(warehouseInfo.getQuantity());
            outWarehouseInfo.setTransactionType(1);
            outWarehouseInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            outWarehouseInfo.setDeliveryOrderNumber(warehouseOutRecord.getCode());
            outWarehouseInfo.setUnitPrice(warehouseInfoFromStock.getUnitPrice());
            addList.add(outWarehouseInfo);
        }
        this.updateBatchById(updateList);
        this.saveBatch(addList);
        return warehouseOutRecordService.save(warehouseOutRecord);
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
