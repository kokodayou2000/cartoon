package org.example.request.cartoon;

import lombok.Data;


import java.util.Set;

@Data
public class CreateCartoonReq {
    // 标题
    private String title;

    // 介绍
    private String introduction;

    private Integer price;

    // 标签
    private Set<String> tags;

}
