package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseUser {

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String name;

    /**
     * 邮箱
     */
    private String mail;
}
