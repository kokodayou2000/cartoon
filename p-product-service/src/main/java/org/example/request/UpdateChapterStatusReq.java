package org.example.request;

import lombok.Data;

@Data
public class UpdateChapterStatusReq {

    private String chapterId;

    // doing or finish
    private String status;
}
