package org.example.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * 给用户显示的详情
 */
@Data
public class UserVO {

    private String id;

    /**
     * 昵称
     */
    private String name;

    private String headImg;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 邮箱
     */
    private String mail;

}
