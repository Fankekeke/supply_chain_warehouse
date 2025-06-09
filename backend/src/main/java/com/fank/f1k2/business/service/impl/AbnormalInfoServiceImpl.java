package com.fank.f1k2.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fank.f1k2.business.entity.AbnormalInfo;
import com.fank.f1k2.business.dao.AbnormalInfoMapper;
import com.fank.f1k2.business.service.IAbnormalInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK fan1ke2ke@gmail.com（悲伤的橘子树）
 */
@Service
public class AbnormalInfoServiceImpl extends ServiceImpl<AbnormalInfoMapper, AbnormalInfo> implements IAbnormalInfoService {

    /**
     * 分页获取异常反馈
     *
     * @param page      分页对象
     * @param queryFrom 异常反馈
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPage(Page<AbnormalInfo> page, AbnormalInfo queryFrom) {
        return baseMapper.queryPage(page, queryFrom);
    }
}
