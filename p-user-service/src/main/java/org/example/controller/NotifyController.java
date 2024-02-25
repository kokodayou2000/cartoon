package org.example.controller;

import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.TimeConstants;
import org.example.enums.BizCodeEnum;
import org.example.enums.SendCodeEnum;
import org.example.exceptions.BizException;
import org.example.service.NotifyService;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/notify")
@Slf4j
public class NotifyController {
    @Autowired
    private Producer captchaProducer;
    //设置 泛型才能解决的redis序列化和反序列化的问题
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //短信发送等服务
    @Autowired
    private NotifyService notifyService;


    @GetMapping("/getChapter")
    public void getCaptcha(HttpServletRequest req, HttpServletResponse resp) {
        String text = captchaProducer.createText();
        log.info("图形验证码"+text);
        BufferedImage image = captchaProducer.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = resp.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
            String captchaKey = getCaptchaKey(req);
            log.info("captchaKey = {}",captchaKey);
            //存放到redis中
            redisTemplate.opsForValue().set(captchaKey,text, TimeConstants.EXPIRE_TIME_3, TimeUnit.MILLISECONDS);
            outputStream.flush();

        } catch (IOException e) {
            log.error("验证码获取异常");
            throw new BizException(BizCodeEnum.CODE_CAPTCHA_REQUEST_ERROR.getCode(),BizCodeEnum.CODE_CAPTCHA_REQUEST_ERROR.getMessage());
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new BizException(-1,"输出流关闭异常"+e.getMessage());
            }
        }
    }


    @GetMapping("/sendCode")
    public JsonData sendRegisterCode(@RequestParam(value = "to") String to,
                                     @RequestParam(value = "captcha") String captcha,
                                     HttpServletRequest request
                                     ){

        String key = getCaptchaKey(request);
        log.info("sendCode key = {}",key);
        //获取redis中的数据，查看是否存在该验证码
        String code = redisTemplate.opsForValue().get(key);
        log.info("in redis {}",code);
        if (captcha != null && code != null && captcha.equalsIgnoreCase(code)){
            //成功
            //需要考虑高并发下的问题
            redisTemplate.delete(key);
            JsonData jsonData = notifyService.sendCaptchaCode(SendCodeEnum.USER_REGISTER, to);
            return jsonData;

        }else {
            //失败
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA_ERROR);
        }

    }





    /**
     * 根据请求获取ip和请求头，并返回格式化之后的字符串
     * @param req
     * @return
     */
    private String getCaptchaKey(HttpServletRequest req){
        String ipAddr = CommonUtil.getIpAddr(req);
        String reqHeader = req.getHeader("User-Agent");
        String key = "user-service:captcha:"+ CommonUtil.MD5(ipAddr+reqHeader);
        log.info("ip={}",ipAddr);
        log.info("userAgent={}",reqHeader);
        log.info("key={}",key);


        return key;
    }


}
