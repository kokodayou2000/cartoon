package org.example.request.cartoon;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateCartoonReq {

    private String id;

    // 标题
    private String title;

    // 介绍
    private String introduction;

    // 标签
    private Set<String> tags;
    
}
