package org.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_order_item")
public class ProductOrderItemDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private Long productOrderId;


    private String outTradeNo;

    /**
     * 产品id,对应章节id
     */
    private String productId;

    /**
     * 商品名称,对应章节名称
     */
    private String productName;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 购物项商品单价 同理 price
     */
    private Integer amount;

}
