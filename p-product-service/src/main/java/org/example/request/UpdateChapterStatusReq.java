package org.example.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateChapterStatusReq {

    private String chapterId;

    // doing or finish
    private String status;

    private String url;
}
