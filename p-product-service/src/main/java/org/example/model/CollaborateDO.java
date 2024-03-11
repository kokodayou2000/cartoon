package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.Set;

@Data
@Document("collaborate")
public class CollaborateDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    // 地址
    private String imgUrl;

    // 详情
    private String info;

    // 漫画id
    private String cartoonId;

    // 漫画名
    private String cartoonName;

    // 参与者id
    private String patternId;

    // 章节id
    private String chapterId;

    // 章节名
    private String chapterName;

    // 本页对应的页码
    private Integer num;

    // 上传时间
    private Date uploadTime;

    // 是否被漫画作者通过
    private Boolean pass;

    // 是否被删除
    private Boolean del;
}

