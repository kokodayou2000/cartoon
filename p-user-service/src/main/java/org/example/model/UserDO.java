package org.example.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 头像
     */
    private String headImg;


    /**
     * 积分
     */
    private Integer points;

    private Date createTime;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 盐，用于个人敏感信息处理
     */
    private String secret;


}
