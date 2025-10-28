package com.lyy.wxloginlearn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YueYang
 * Created on 2025/10/27 12:10
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss.ali")
public class AliOSSProperties {
    private String keyId;

    private String keySecret;

    private String bucketName;

    private String endpoint;

    private String region;

    private String directory;
}
