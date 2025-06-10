package com.fank.f1k2.business.service;

import com.alipay.api.AlipayApiException;
import com.fank.f1k2.common.domain.AlipayBean;

public interface IPayService {

    /**
     * 支付宝支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}
