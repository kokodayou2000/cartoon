package org.example.request.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.request.base.UploadReq;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UpdateChapterStatusReq  extends UploadReq {

    // doing or finish
    private String status;
}
