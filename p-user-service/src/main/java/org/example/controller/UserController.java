package org.example.controller;


import org.example.core.AjaxResult;
import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.service.UserService;
import org.example.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 查看用户是否存在 feign
    @GetMapping("/exist/userId")
    public AjaxResult exist(String userId){
        return userService.exist(userId);
    }


    @PostMapping("/avatar")
    public AjaxResult avatar(
            @RequestPart("file") MultipartFile file
    ){
        return userService.uploadAvatar(file);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody UserRegisterRequest userRegisterRequest){

        return userService.register(userRegisterRequest);

    }

    @PostMapping("/login")
    public AjaxResult login(
            @RequestBody UserLoginRequest userLoginRequest){
        return userService.login(userLoginRequest);
    }



    @GetMapping("/detail")
    public AjaxResult detail(){
        UserVO userVo = userService.findUserDetail();
        return AjaxResult.success(userVo);

    }


}

