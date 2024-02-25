package org.example.component;

import lombok.extern.slf4j.Slf4j;
import org.example.config.PayUrlConfig;
import org.example.vo.PayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WechatPayStrategy implements PayStrategy {

    @Autowired
    private PayUrlConfig urlConfig;

    @Override
    public String unifiedOrder(PayInfoVO payInfoVO) {
        return null;
    }

    @Override
    public String refund(PayInfoVO payInfoVO) {
        return PayStrategy.super.refund(payInfoVO);
    }

    @Override
    public String queryPaySuccess(PayInfoVO payInfoVO) {
        return PayStrategy.super.queryPaySuccess(payInfoVO);
    }
}
