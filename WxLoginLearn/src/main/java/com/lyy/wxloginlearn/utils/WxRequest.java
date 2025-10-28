package com.lyy.wxloginlearn.utils;


import com.google.gson.Gson;
import com.lyy.wxloginlearn.config.WxPropertiesConfig;
import com.lyy.wxloginlearn.entity.dto.AccessKeyResponseDTO;
import com.lyy.wxloginlearn.entity.dto.WxPhoneResponseDTO;
import com.lyy.wxloginlearn.entity.dto.WxRequestByLoginCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * @author YueYang
 * Created on 2025/10/25 12:22
 * @version 1.0
 */
@Component
public class WxRequest {
    /**
     * RestTemplate 是 Spring Framework 提供的一个用于执行 HTTP 请求的同步客户端，它简化了与 RESTful Web Services 的交互
     * 它支持多种 HTTP 方法（GET、POST、PUT、DELETE 等），并可以处理不同的媒体类型（如 JSON、XML 等）
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取配置文件中的appid等属性
     */
    @Autowired
    private WxPropertiesConfig wxPropertiesConfig;

    @Autowired
    @Qualifier("MyGson")
    private Gson GSON;

    /**
     * 微信小程序用户登录凭证校验接口
     * 用于获取用户的 openid 和 session_key
     * 注意这里{} 是占位符，需要在实际请求时替换为具体的值
     * 例如：openIdUrl.replace("{appid}", wxPropertiesConfig.getAppid())
     * .replace("{secret}", wxPropertiesConfig.getSecret())
     * .replace("{js_code}", wxLoginDTO.getLoginCode());
     */
    private static final String openIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code";


    /**
     * 微信后端获取用户手机号接口
     */
    private static final String phoneUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token={access_token}";

    /**
     * 微信小程序用户登录凭证校验接口文档
     * 这个接口用来获取access_token 通过这个凭证可以获取用户的手机号
     */
    private static final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";

    /**
     * 根据登录凭证获取用户的 openid 和 session_key
     *
     * @param loginCode 登录凭证openId
     * @return 返回接收到的结果
     */
    public WxRequestByLoginCodeDTO getLoginParamsByLoginCode(String loginCode) {

        //向微信后端发送请求 携带loginCode
        String loginStrRes = restTemplate.getForObject(openIdUrl, String.class, wxPropertiesConfig.getAppid(), wxPropertiesConfig.getSecret(), loginCode);
        //将返回的json字符串转换为对象
        return GSON.fromJson(loginStrRes, WxRequestByLoginCodeDTO.class);
    }


    /**
     * 获取手机号的access_token 这个一般两个小时内有效
     */
    public AccessKeyResponseDTO getAccessToken() {
        //向微信后端发送请求 携带appid和secret
        String accessTokenRes = restTemplate.getForObject(accessTokenUrl, String.class, wxPropertiesConfig.getAppid(), wxPropertiesConfig.getSecret());
        //将返回的json字符串转换为对象
        return GSON.fromJson(accessTokenRes, AccessKeyResponseDTO.class);
    }


    /**
     * 根据手机号凭证获取用户手机号
     *
     * @param phoneCode 手机号凭证
     * @param opedId    用户openid
     * @return 返回接收到的结果
     */
    public WxPhoneResponseDTO WxRequestGetPhoneNumber(String phoneCode, String opedId, String access_token) {

        //1. 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        //2.创建请求体
        Map<String, String> body = new HashMap<>();
        body.put("code", phoneCode);
        // body.put("openid", opedId);

        //3. 创建HttpEntity对象 包含请求头和请求体
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        //4. 发送POST请求
        String url = phoneUrl.replace("{access_token}", access_token);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        //5. 从ResponseEntity中获取响应体
        String responseBody = stringResponseEntity.getBody();
        //将返回的json字符串转换为对象
        return GSON.fromJson(responseBody, WxPhoneResponseDTO.class);


    }


}
