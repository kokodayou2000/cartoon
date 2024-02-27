package org.example.config;

import feign.RequestInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 核心集群中创建一个公共的Config即可
 * 如果是隔离集群的话，每个服务都应该有
 * IOC 容器
 */
@Configuration
@Data
@Slf4j
public class AppConfig {

    // 通过 ioc注入的方式解决 feign远程调用无法使用token的问题
    // 这里使用的设计模式 IOC 装饰器？
    @Bean
    public RequestInterceptor requestInterceptor(){
        //
        log.info("InRequest");
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            String token = requestAttributes.getRequest().getHeader("token");
            requestTemplate.header("token",token);

        };
    }
}
