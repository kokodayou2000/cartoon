package org.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.core.AjaxResult;
import org.example.enums.SendCodeEnum;
import org.example.fegin.IFileServer;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.BaseUser;
import org.example.model.ImageDO;
import org.example.model.UserDO;
import org.example.mapper.UserMapper;
import org.example.model.UserInfo;
import org.example.request.UserChargeReq;
import org.example.request.UserLoginRequest;
import org.example.request.UserRegisterRequest;
import org.example.response.LoginResp;
import org.example.service.NotifyService;
import org.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.utils.JWTUtil;
import org.example.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {


    @Autowired
    private NotifyService notifyService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IFileServer uploadAvatar;

    @Override
    public AjaxResult uploadAvatar(MultipartFile file) {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        AjaxResult result = uploadAvatar.uploadAvatar(file);
        Integer code = (Integer)result.get("code");
        if (code != 200){
            return AjaxResult.error("上传头像失败");
        }
        String userId = baseUser.getId();
        UserDO userDO = userMapper.selectById(userId);
        Object imageDOMap = (Object) (result.get("data"));
        ObjectMapper objectMapper = new ObjectMapper();
        ImageDO imageDO = objectMapper.convertValue(imageDOMap, ImageDO.class);
        userDO.setHeadImg(imageDO.getUrl());
        int updateById = userMapper.updateById(userDO);
        if (updateById != 1){
            return AjaxResult.error("更新头像失败");
        }
        return result;
    }

    @Override
    public AjaxResult exist(String userId) {
        UserDO userDO = userMapper.selectById(userId);
        if (userDO != null){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @Override
    public AjaxResult charge(UserChargeReq req) {
        String userId = req.getUserId();
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null){
            return AjaxResult.error("查找用户失败");
        }
        userDO.setPoints(userDO.getPoints() + req.getPoint());
        userMapper.updateById(userDO);
        return AjaxResult.success("充值成功");
    }

    @Override
    public AjaxResult balance(String userId) {
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null){
            return AjaxResult.error("查找用户失败");
        }
        return AjaxResult.success(userDO.getPoints());
    }

    @Override
    public UserVO search(String userId) {
        UserDO userDO = userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO,userVO);
        return userVO;
    }

    @Override
    public List<UserVO> batchSearch(List<String> userIdList) {
        List<UserDO> userDOS = userMapper.selectBatchIds(userIdList);
        return userDOS.stream().map((item) -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(item, userVO);
            return userVO;
        }).collect(Collectors.toList());

    }

    @Override
    public AjaxResult searchUser(String username) {
        List<UserDO> byUsername = userMapper.findByUsername(username);
        List<UserVO> voList = byUsername.stream().map((item) -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(item, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return AjaxResult.success(voList);
    }

    @Override
    public AjaxResult info(String id) {
        UserDO userDO = userMapper.selectById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO,userVO);
        return AjaxResult.success(userVO);
    }

    @Override
    public AjaxResult pay(UserChargeReq req) {
        String userId = req.getUserId();
        UserDO userDO = userMapper.selectById(userId);
        if (userDO == null){
            return AjaxResult.error("查找用户失败");
        }
        int result = userDO.getPoints() - req.getPoint();
        if (result < 0){
            return AjaxResult.error("用户余额不足");
        }
        userDO.setPoints(result);
        userMapper.updateById(userDO);
        return AjaxResult.success("支付成功");
    }




    /**
     * 用户注册
     * 邮箱验证码验证
     * 数据加密
     * 账号唯一性检查
     * 插入数据库
     * 新用户福利
     * @param userRegisterRequest 用户注册请求
     * @return 注册结果
     */
    @Override
    @Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRED)
//    @GlobalTransactional
    public AjaxResult register(UserRegisterRequest userRegisterRequest) {

        //检查验证码的flag
        boolean checkCode = false;
        //验证码验证
        if (StringUtils.isNoneBlank(userRegisterRequest.getMail())){
            checkCode = notifyService.checkCaptchaCode(SendCodeEnum.USER_REGISTER, userRegisterRequest.getMail(), userRegisterRequest.getCode());
        }
        if (!checkCode){
            return AjaxResult.error("验证码错误");
        }
        //创建数据库实体类
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userRegisterRequest,userDO);
        userDO.setCreateTime(new Date());
        //设置密码 MD5+salt

        //生成salt
        userDO.setSecret("$1$"+ RandomStringUtils.random(8,"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));


        //根据密码和salt生成加密数据
        String crypt = Md5Crypt.md5Crypt(
                userRegisterRequest.getPwd().getBytes(StandardCharsets.UTF_8),
                userDO.getSecret()
        );

        userDO.setPwd(crypt);

        //账号唯一性的检查 检测是否重复注册的问题
        if (checkUnique(userDO.getMail())){
            return AjaxResult.error("邮箱重复注册");
        }

        //数据库写入
        int rows = userMapper.insert(userDO);
        log.info("影响的行数:{} 注册的对象{}",rows,userDO);


        return AjaxResult.success("注册成功");
    }

    /**
     * 1.根据mail查找是否存在这条记录
     *  1.1 根据salt+明文密码进行md5加密，然后和数据库的密文进行匹配
     * @param userLoginRequest 用户请求登录的实体类
     * @return JsonData 结果
     */
    @Override
    public AjaxResult login(UserLoginRequest userLoginRequest) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getMail,userLoginRequest.getMail());
        List<UserDO> userDOList = userMapper.selectList(wrapper);
        if (userDOList != null && userDOList.size() == 1){
            //已经注册了
            UserDO userDO = userDOList.get(0);
            String crypt = Md5Crypt.md5Crypt(userLoginRequest.getPwd().getBytes(), userDO.getSecret());
            if (crypt.equals(userDO.getPwd())){
                //密码正确,生成Token
                BaseUser loginUser = new BaseUser();
                BeanUtils.copyProperties(userDO,loginUser);
                String token = JWTUtil.generationJsonWebToken(loginUser);
                //将token返回
                JWTUtil.checkJWT(token);
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(userDO,userInfo);
                LoginResp loginResp = new LoginResp();
                loginResp.setUserInfo(userInfo);
                loginResp.setToken(token);
                return AjaxResult.success(loginResp);
            }else{
                //密码不正确
                return AjaxResult.error("密码错误");
            }


        }else{
            //未注册
            return AjaxResult.error("未注册");
        }


    }

    @Override
    public UserVO findUserDetail() {
        BaseUser loginUser = TokenCheckInterceptor.tl.get();

        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(
                UserDO::getId,loginUser.getId()
        );

        UserDO userDo = userMapper.selectOne(queryWrapper);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDo,userVO);

        return userVO;
    }



    /**
     * 校验用户邮箱是否以及被注册
     * @param mail 用户注册的邮箱
     * @return 校验结果
     */
    private boolean checkUnique(String mail) {
        //其实在数据库中已经做了UNIQUE KEY的校验了，所以这一步其实可以删除掉
        if (userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getMail,mail)) != null){
            return true;
        }
        return false;
    }

}
