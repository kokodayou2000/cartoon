package org.example.request.collaborate;

import lombok.Data;



@Data
public class CreateCollaborateReq {

    // 地址
    private String imgUrl;

    // 详情
    private String info;

    // 章节id
    private String chapterId;

    // 章节名
    private String chapterName;

    // 漫画id
    private String cartoonId;

    // 漫画名
    private String cartoonName;

    // 本页对应的页码
    private Integer num;
}
