package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.interceptor.TokenCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class interceptorConfig  implements WebMvcConfigurer {


    public TokenCheckInterceptor loginInterceptor(){
        return new TokenCheckInterceptor();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // path /api/<model>/<version>/<action>/
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/v1/user/*/**")
                //文件上传和用户注册都不需要进行token验证
                .excludePathPatterns(
                        "/api/*/user/register",
                        "/api/*/user/login",
                        "/api/*/user/exist",
                        "/api/*/user/balance/*",
                        "/api/*/user/pay/*",
                        "/api/*/user/charge"
                );

    }
}
