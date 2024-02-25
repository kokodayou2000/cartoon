package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.interceptor.TokenCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加token验证的拦截器
        registry.addInterceptor(new TokenCheckInterceptor())
                .addPathPatterns(
                        "/api/v1/banner/**",
                        "/api/v1/cartoon/**",
                        "/api/v1/chapter/**",
                        "/api/v1/paper/**"
                )
                .excludePathPatterns(
                        "/api/v1/banner/list",
                        "/api/v1/cartoon/recommend",
                        "/api/v1/cartoon/list/**",
                        "/api/v1/cartoon/cartoonInfo/**",
                        "/api/v1/chapter/chapterInfo/**"
                );
    }

}
