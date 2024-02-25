package org.example.request;

import lombok.Data;

/**
 * 用户登录请求接口
 */
@Data
public class UserLoginRequest {

    private String mail;

    private String pwd;
}
