package org.example.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {


    @Bean
    @Qualifier("captchaProducer")
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties props = new Properties();

        /*
        //边框
        props.setProperty(Constants.KAPTCHA_BORDER,"yes");
        //边框颜色
        props.setProperty(Constants.KAPTCHA_BORDER_COLOR,"200,100,0");
        //文本颜色
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"100,100,100");
        //图像宽度
        props.setProperty(Constants.KAPTCHA_IMAGE_WIDTH,"150");
        //图像高度
        props.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT,"30");
        //文字大小
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"25");
        //讲验证码保存到session中
        props.setProperty(Constants.KAPTCHA_SESSION_KEY,"code");
         */

        //验证码个数
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");
        //验证码字体名称
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES,"Courier");
        //字体间隔
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE,"8");
        //干扰线颜色
        props.setProperty(Constants.KAPTCHA_NOISE_COLOR,"white");
        //干扰实体类
        props.setProperty(Constants.KAPTCHA_NOISE_IMPL,"com.google.code.kaptcha.impl.NoNoise");
        //图片样式
        props.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL,"com.google.code.kaptcha.impl.WaterRipple");
        //文字来源
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING,"0123456789");
        Config config = new Config(props);
        kaptcha.setConfig(config);
        return kaptcha;


    }

}
