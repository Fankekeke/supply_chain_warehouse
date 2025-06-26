package com.fank.f1k2.business.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 分页获取采购订单
     *
     * @param page      分页对象
     * @param queryFrom 采购订单
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPage(Page<OrderInfo> page, @Param("queryForm") OrderInfo queryFrom);

    /**
     * 查询总支出
     *
     * @return 结果
     */
    BigDecimal selectOrderPrice();

    /**
     * 获取本月订单信息
     *
     * @return 结果
     */
    List<OrderInfo> selectOrderByMonth();

    /**
     * 获取本年订单信息
     *
     * @return 结果
     */
    List<OrderInfo> selectOrderByYear();

    /**
     * 十天内订单数量统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumWithinDays(@Param("supplierId") Integer supplierId);

    /**
     * 十天内订单支出统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderPriceWithinDays(@Param("supplierId") Integer supplierId);

    /**
     * 订单类别统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderType();
}
