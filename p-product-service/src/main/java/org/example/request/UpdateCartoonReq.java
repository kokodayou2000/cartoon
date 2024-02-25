package org.example.request;

import lombok.Data;

import java.util.List;
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
