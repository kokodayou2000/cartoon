package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Document("cartoon")
public class CartoonDO {

    @Indexed(unique = true)
    @Id
    @MongoId
    @Field("_id")
    private String id;

    // 标题
    private String title;

    // 销量
    private Integer salesNum;

    // 介绍
    private String introduction;

    // 封面链接
    private String coverUrl;

    // 标题
    private Set<String> tags;

    // 状态 连载 Or 完结
    private String status;

    // 单章节的价格
    private Integer price;

    // 合作者
    private Set<String> partners;

    // 创建者
    private String createBy;

    // 创建时间
    private Date createTime;

    // 最后更新时间
    private Date lastUpdateTime;

}

