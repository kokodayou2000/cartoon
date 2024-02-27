package org.example.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartOrderItemVO {

    // 章节 id 列表
    private List<String> chapterList;

    // 漫画 id
    private String cartoonId;

}
