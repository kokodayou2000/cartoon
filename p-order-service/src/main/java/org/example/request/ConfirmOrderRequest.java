package org.example.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class ConfirmOrderRequest {

    /**
     * 购买的章节列表
     */
    private Set<String> chapterIdList;

    /**
     * 商品id
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
