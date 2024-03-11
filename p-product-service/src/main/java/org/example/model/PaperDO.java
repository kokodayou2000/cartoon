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
@Document("paper")
public class PaperDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    // 对应的 章节 id
    private String chapterId;

    // 页编号
    private Integer num;

    // 链接
    private String url;


    //信息
    private String info;

    // 合作者
//    private Set<String> partners;
    // 创作者
    private String createBy;
}

