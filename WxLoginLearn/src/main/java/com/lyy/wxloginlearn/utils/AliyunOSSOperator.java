package com.lyy.wxloginlearn.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.lyy.wxloginlearn.config.AliOSSProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

import java.util.UUID;

@Component
public class AliyunOSSOperator {

    @Autowired
    private AliOSSProperties aliOSSProperties;

    private String endpoint;
    private String bucketName;
    private String region;
    private String directory;
    private String accessKeyId;
    private String accessKeySecret;

    @PostConstruct
    public void init() {
        this.endpoint = aliOSSProperties.getEndpoint();
        this.bucketName = aliOSSProperties.getBucketName();
        this.region = aliOSSProperties.getRegion();
        this.directory = aliOSSProperties.getDirectory();
        this.accessKeyId = aliOSSProperties.getKeyId();
        this.accessKeySecret = aliOSSProperties.getKeySecret();
    }

    public String upload(byte[] content, String originalFilename) throws Exception {


        //生成一个新的不重复的文件名
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = directory + "/" + newFileName;

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(new DefaultCredentialProvider(accessKeyId, accessKeySecret))
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } finally {
            ossClient.shutdown();
        }


        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }

}
