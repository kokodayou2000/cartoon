package org.example.response;

import lombok.Data;
import org.example.model.UserInfo;

@Data
public class LoginResp {

    private UserInfo userInfo;

    private String token;
}
