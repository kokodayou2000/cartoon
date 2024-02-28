package org.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chapter_item")
public class ChapterItemDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private String userId;

    private String cartoonId;

    private String chapterId;

    private String chapterName;

    private String status;

    private String outTradeNo;

    private Date createTime;

    private Integer price;

}
