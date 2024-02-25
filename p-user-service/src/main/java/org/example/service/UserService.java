package org.example.service;

import org.example.core.AjaxResult;
import org.example.model.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.request.UserPointReq;
import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;


public interface UserService extends IService<UserDO> {


    AjaxResult register(UserRegisterRequest userRegisterRequest);


    AjaxResult login(UserLoginRequest userLoginRequest);


    UserVO findUserDetail();

    AjaxResult uploadAvatar(MultipartFile file);

    AjaxResult exist(String userId);

    AjaxResult charge(UserPointReq req);

    AjaxResult pay(UserPointReq req);

    AjaxResult balance(String userId);
}
