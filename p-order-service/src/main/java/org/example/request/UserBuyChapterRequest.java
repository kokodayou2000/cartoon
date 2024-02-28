package org.example.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserBuyChapterRequest {

    /**
     * 购买的章节列表
     */
    private Set<String> chapterIdList;

    /**
     * 漫画id
     */
    private String cartoonId;

    /**
     * 漫画名称
     */
    private String cartoonName;

    // 客户端平台
    private String clientType;
    /**
     * 支付方式
     */
    private String payType;

    /**
     * 总价格
     */
    private Integer totalAmount;

}
