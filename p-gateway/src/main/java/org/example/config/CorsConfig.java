package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.addAllowedOrigin("*"); // 允许所有来源
        corsConfig.addAllowedMethod("*"); // 允许所有HTTP方法
        corsConfig.addAllowedHeader("*"); // 允许所有HTTP头部
        corsConfig.addAllowedOrigin("http://127.0.0.1:8777");
        corsConfig.addAllowedOrigin("http://10.12.0.143:8777");
        System.out.println("跨域过滤器");
        return new CorsWebFilter(source -> corsConfig);
    }
}