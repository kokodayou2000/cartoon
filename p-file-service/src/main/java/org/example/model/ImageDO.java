package org.example.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("image")
public class ImageDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    private int width;

    private int height;

    private String url;

}
