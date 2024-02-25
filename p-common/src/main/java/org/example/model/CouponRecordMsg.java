package org.example.model;

import lombok.Data;

@Data
public class CouponRecordMsg {
    /**
     * 消息id
     */
    private Long msgId;
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 库存锁定任务id
     */
    private Long taskId;

}
