package com.fank.f1k2.business.service.impl;

import com.alipay.api.AlipayApiException;
import com.fank.f1k2.business.service.IPayService;
import com.fank.f1k2.common.domain.AlipayBean;
import com.fank.f1k2.common.utils.Alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }
}