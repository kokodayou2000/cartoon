package org.example.request;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateChapterReq {

    private String id;

    // 编号
    private Integer num;

    // 标题
    private String title;


    
}
