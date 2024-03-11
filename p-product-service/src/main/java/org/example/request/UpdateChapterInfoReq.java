package org.example.request;

import lombok.Data;

@Data
public class UpdateChapterInfoReq {

    private String id;

    // 编号
    private Integer num;

    // 标题
    private String title;

}
