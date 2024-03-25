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
@Document("rawPad")
public class RawPadDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    // 页id
    private String paperId;

    // 用户id
    private String userId;


    // 笔画
    private List<Pen> penList;

}



