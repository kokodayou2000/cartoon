package org.example.service;

import org.example.core.AjaxResult;
import org.example.model.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.request.UserChargeReq;
import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService extends IService<UserDO> {


    AjaxResult register(UserRegisterRequest userRegisterRequest);


    AjaxResult login(UserLoginRequest userLoginRequest);


    UserVO findUserDetail();

    AjaxResult uploadAvatar(MultipartFile file);

    AjaxResult exist(String userId);

    AjaxResult charge(UserChargeReq req);

    AjaxResult pay(UserChargeReq req);

    AjaxResult balance(String userId);

    UserVO search(String userId);

    List<UserVO> batchSearch(List<String> userIdList);

    AjaxResult searchUser(String username);

    AjaxResult info(String id);

}
