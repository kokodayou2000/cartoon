package org.example.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private String name;

    private String pwd;

    private String mail;

    private String code;

}
