package org.example.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ChargeRequest {


    /**
     * 充值的id
     */
    private String shopId;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 总价格
     */
    private BigDecimal totalAmount;

}
