package org.example.request;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Set;

@Data
public class CreatePaperReq {


    // 对应的 章节 id
    private String chapterId;

    // 页编号，这个不能重复
    private Integer num;

    // 链接
    private String url;

    // 信息
    private String info;

}
