package org.example.controller;


import org.example.core.AjaxResult;
import org.example.request.UserChargeReq;
import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.service.UserService;
import org.example.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info/{id}")
    public AjaxResult info(
            @PathVariable("id") String id){
        return userService.info(id);
    }


    @GetMapping("/searchUser/{username}")
    public AjaxResult searchUser(
            @PathVariable("username") String username){
        return userService.searchUser(username);
    }

    // 查看用户是否存在 feign
    @GetMapping("/exist/{userId}")
    public AjaxResult exist(
            @PathVariable("userId") String userId){
        return userService.exist(userId);
    }

    // 充值
    @PostMapping("/charge")
    public AjaxResult charge(
            @RequestBody UserChargeReq req
    ){
        return userService.charge(req);
    }

    // 余额 这个接口应该只能内部调用
    // 查看用户剩余 point
    @GetMapping("/balance/{userId}")
    public AjaxResult balance(@PathVariable("userId") String userId){
        return userService.balance(userId);
    }

    // 充值
    @PostMapping("/pay")
    public AjaxResult pay(
            @RequestBody UserChargeReq req
    ){
        return userService.pay(req);
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

    @GetMapping("/search/{userId}")
    public AjaxResult search(
            @PathVariable("userId") String userId
    ){
        UserVO userVo = userService.search(userId);
        return AjaxResult.success(userVo);

    }

    @PostMapping("/batchSearch")
    public AjaxResult batchSearch(
            @RequestBody List<String> userIdList
    ){
        List<UserVO> userVo = userService.batchSearch(userIdList);
        return AjaxResult.success(userVo);
    }


}

