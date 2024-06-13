package org.example.request.paper;

import lombok.Data;

@Data
public class CreatePaperReq {

    // 对应的 章节 id
    private String chapterId;

    // 页编号，这个不能重复
    private Integer num;

    // 信息
    private String info;

}
