package org.example.service;

import org.example.enums.SendCodeEnum;
import org.example.utils.JsonData;

/**
 * 提醒服务
 */
public interface NotifyService {
    /**
     * 发送验证码
     * @param sendCodeEnum  发送的类型
     * @param to 发送的对象
     * @return Json数据
     */
    JsonData sendCaptchaCode(SendCodeEnum sendCodeEnum,String to);


    /**
     * 验证码进行验证 邮箱发送的验证码和手机发送的验证码两种
     * @param sendCodeEnum 发送的类型
     * @param to 发送的对象
     * @param code 发送的内容
     * @return 验证结果
     */
    boolean checkCaptchaCode(SendCodeEnum sendCodeEnum,String to,String code);

}
