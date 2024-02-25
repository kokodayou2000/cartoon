package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.constant.CacheKey;
import org.example.constant.TimeConstants;
import org.example.enums.BizCodeEnum;
import org.example.enums.SendCodeEnum;
import org.example.component.MailService;
import org.example.service.NotifyService;
import org.example.utils.CheckUtil;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    MailService mailService;


    //存放字符类型数据的redis模板
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 邮箱主题
     */
    private static final String SUBJECT = "邮箱验证码注册";
    /**
     * 验证码内容
     */
    private static final String CONTENT = "您的邮箱验证码是%s,有效时间是3分钟，请勿将验证码交给陌生人";



    /**
     * 发送验证码
     * 1.存储验证码到缓存
     * 2.发送验证码到邮箱或者手机
     *
     * @param sendCodeEnum  发送的类型
     * @param to 发送的对象
     * @return 发送结果
     */
    @Override
    public JsonData sendCaptchaCode(SendCodeEnum sendCodeEnum, String to) {

        if (sendCodeEnum == null){
            return JsonData.buildError("类型不应该为空");
        }
        if (to == null){
            return JsonData.buildError("发送的对象不应该为空");
        }

        //  example code:%s:%s  -> code:user_register:1611601667@qq.com
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeEnum.name(),to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);

        //如果不为空，则判断是否60s内重复发送
        if (StringUtils.isNoneBlank(cacheValue)){
            // TODO
            String[] split = cacheValue.split("_");
            long ttl = 0L;
            if (split.length == 2){
                ttl = Long.parseLong(split[1]);
            }else{
                throw new RuntimeException("redis 格式错误");
            }

            //比较 当前时间戳-验证码发送时间戳 < 6000 就不给发送
            long intervalTime = CommonUtil.getCurrentTimestamp() - ttl;
            if ( intervalTime < TimeConstants.EXPIRE_TIME_1){
                log.info("重复发送验证码,时间间隔{}",intervalTime/1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }

        }
        // 拼接验证码
        String code = CommonUtil.getRandomCode(4);

        // <xxx>_xxx
        String value = code+"_"+CommonUtil.getCurrentTimestamp();

        // 过期时间为10分钟
        redisTemplate.opsForValue().set(cacheKey,value, TimeConstants.EXPIRE_TIME_10, TimeUnit.MILLISECONDS);

        if (CheckUtil.isEmail(to)){
            //邮箱验证码
            mailService.sendMail(to,SUBJECT,String.format(CONTENT,code));


            return JsonData.buildSuccess("邮箱验证码发送成功");
        }else if (CheckUtil.isPhone(to)){
            //短信验证码


        }

        return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA_SEND_ERROR);
    }

    /**
     * 校验验证码
     * @param sendCodeEnum 发送的类型
     * @param to 发送的对象
     * @param code 发送的内容
     * @return 校验结果
     */
    @Override
    public boolean checkCaptchaCode(SendCodeEnum sendCodeEnum, String to, String code) {
        //构建再缓存中的key名称  code:xx:xx
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeEnum.name(),to);
        //通过redis获取到值
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);

        if (StringUtils.isNoneBlank(cacheValue)){
            //验证码格式 <验证码>_<时间戳>
            String cacheCode = cacheValue.split("_")[0];
            String ttl = cacheValue.split("_")[1];
            if (Long.parseLong(ttl) - TimeConstants.EXPIRE_TIME_3 <= 0 ){
                log.error("验证码已经超时");
                return false;
            }

            //匹配验证码是否正确
            if (cacheCode.equals(code)){
                //删除redis中的验证码
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }
}
