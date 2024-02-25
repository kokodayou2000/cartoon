package org.example.component;

import org.example.vo.PayInfoVO;

public interface PayStrategy {

    /**
     * 下单
     * @param payInfoVO
     * @return
     */
    String unifiedOrder(PayInfoVO payInfoVO);


    default String refund(PayInfoVO payInfoVO){return "";}

    /**
     * 查询支付是否成功
     * @param payInfoVO
     * @return
     */
    default String queryPaySuccess(PayInfoVO payInfoVO) {return "";}
}
