package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInfoVO {
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 订单总金额
     */
    private BigDecimal payFee;

    /**
     * 支付类型 微信 or 支付宝 or 银行
     */
    private String payType;

    /**
     * H5 app pc
     */
    private String clientType;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 订单支付超时时间，毫秒
     */
    private long orderPayTimeoutMills;
}
