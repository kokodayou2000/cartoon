package org.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 分页查询，将分页查询抽离出来作为公共配置
 */
@Configuration
public class MyBatisPlusPageConfig {


    /**
     * 分页插件，一级缓存和二级缓存遵循mybatis的规则
     * 需要设置MybatisConfiguration#useDeprecatedExecutor = false 避免出现缓存问题
     */

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //就是新建一个拦截器，然后添加分页拦截的规则
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }



}
