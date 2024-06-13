package org.example.request.chapter;

import lombok.Data;

@Data
public class CreateChapterReq {

    // 对应的 漫画 id
    private String cartoonId;

    // 章节编号
    private Integer num;

    // 是否免费
    private Boolean free;

    // 章节标题
    private String title;

}
