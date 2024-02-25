package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
//这个对应的就是配置文件中以 aliyun.oss开始的内容
@ConfigurationProperties(prefix = "aliyun.oss")
public class OSSConfig {
    //对应的yaml文件会自动的将 end-point转换成 endPoint
    private String endPoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

}
