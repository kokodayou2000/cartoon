package org.example.request.chapter;

import lombok.Data;

@Data
public class AddChapterPatternReq {

    // 章节id
    private String chapterId;

    // 希望新增的参与者 id
    private String patternId;
}
