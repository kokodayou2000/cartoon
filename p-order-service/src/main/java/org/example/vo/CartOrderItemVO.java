package org.example.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartOrderItemVO {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("by_num")
    private Integer byNum;

    @JsonProperty("product_title")
    private String productTitle;

    @JsonProperty("product_img")
    private String productImg;

    private BigDecimal amount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
}
