package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
@ConfigurationProperties(prefix = "spring.minio")
public class MinioConfig {

    private String url;

    private String bucket;

    private String accessKey;

    private String secretKey;
}
