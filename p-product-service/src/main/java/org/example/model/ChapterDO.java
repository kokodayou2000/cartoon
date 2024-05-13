package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Set;

@Data
@Document("chapter")
public class ChapterDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    // 对应完成的paperId，只有当章节完成后才可能获取到
    private String finishedPDFUrl;


    // 对应的 漫画 id
    private String cartoonId;

    // 章节编号
    private Integer num;

    // 章节标题
    private String title;

    // 完成 or 创作中
    private String status;

    // 是否是免费章节
    private Boolean free;

    // 章节大纲
    private String info;

    // 合作者
    private Set<String> partners;

}

