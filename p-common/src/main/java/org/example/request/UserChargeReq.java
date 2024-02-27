package org.example.request;

import lombok.Data;

@Data
public class UserChargeReq {

    // 需要发送的用户id
    private String userId;

    // 目标的金额
    private Integer point;
}
