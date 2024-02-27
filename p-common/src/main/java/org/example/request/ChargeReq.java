package org.example.request;

import lombok.Data;

@Data
public class ChargeReq {

    // 用户充值，token 解析，然后充值
    private String chargeId;


    private String clientType;
}
