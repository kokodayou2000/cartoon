package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

// 临时存储
@Data
@Document("tempStorage")
public class TempStorageDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    // 地址
    private String imgUrl;

    // 其实可以将position 位置记录下来的

    // 上传的用户
    private String userId;


    // 详情
    private String info;

    // 上传时间
    private Date uploadTime;


}

