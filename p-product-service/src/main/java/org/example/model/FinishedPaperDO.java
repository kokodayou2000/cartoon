package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;


/**
 * 假设 paper完成后，就将paper 中的图片列表转换成 pdf
 */
@Data
@Document("finishedPaper")
public class FinishedPaperDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    // 对应的 章节 id
    private String chapterId;

    private String pdfUrl;

}
