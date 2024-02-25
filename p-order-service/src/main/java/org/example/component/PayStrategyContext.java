package org.example.component;

import org.example.vo.PayInfoVO;

public class PayStrategyContext {
    private PayStrategy payStrategy;

    public PayStrategyContext(PayStrategy payStrategy) {
        this.payStrategy = payStrategy;
    }

    // 根据支付策略调用不同的支付
    public String executeUnifiedOrder(PayInfoVO payInfoVO){
        return this.payStrategy.unifiedOrder(payInfoVO);
    }


    // 根据支付的策略，调用不同的查询订单支付状态
    public String executeQueryPaySuccess(PayInfoVO payInfoVO){
        return this.payStrategy.queryPaySuccess(payInfoVO);
    }

}
