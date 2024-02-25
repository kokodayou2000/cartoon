package org.example.component;

import lombok.extern.slf4j.Slf4j;
import org.example.enums.ProductOrderPayTypeEnum;
import org.example.vo.PayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PayFactory {

    @Autowired
    private AlipayStrategy alipayStrategy;

    public String pay(PayInfoVO payInfoVO){
        // 使用支付宝的策略
        PayStrategyContext payStrategyContext = new PayStrategyContext(alipayStrategy);
        return payStrategyContext.executeUnifiedOrder(payInfoVO);
    }

    public String queryPaySuccess(PayInfoVO payInfoVO){
        PayStrategyContext payStrategyContext = new PayStrategyContext(alipayStrategy);
        return payStrategyContext.executeQueryPaySuccess(payInfoVO);
    }
}

