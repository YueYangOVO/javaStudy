package com.lyy.wxloginlearn.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.*;
import com.lyy.wxloginlearn.entity.pojo.WxUser;
import com.lyy.wxloginlearn.exception.ParseTokenException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/10/25 15:20
 * @version 1.0
 */
@Slf4j
@Component
public class JwtUtils {

    //设置token密钥
    private static final String SECRET_KEY = "Love_YueYang@123";

    //导入我自己配置的gson
    @Autowired
    @Qualifier("MyGson")
    private Gson GSON;


    /**
     * 创建token
     *
     * @param jsonUser payload 载荷 存储用户信息
     * @return 返回token字符串
     */
    public static String createToken(String jsonUser) {
        //1.组装头部
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        String token = JWT.create()
                //头部
                .withHeader(header)
                //载荷 设置用户信息 以及30分钟过期
                .withClaim("user", jsonUser)
                //设置token过期时间半个小时
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                //签名
                .sign(Algorithm.HMAC256(SECRET_KEY));
        log.info("token创建成功: {}", token);
        return token;
    }

    /**
     * 解析token
     * 1
     * @param token token字符串
     * @return 是否解析成功
     * 返回0token过期 1token无效  2 token合法
     */
    public static Integer parseToken(String token) {
        //获取token验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build();

        try {
            //verify是解析token的方法 返回解析后的token对象
            DecodedJWT verify = jwtVerifier.verify(token);
            //判断token是否过期
            if (verify.getExpiresAt().before(new Date())) {
                return 0; //token过期
            }
            //token未过期 且合法 则返回true
            return 2; //token合法
        } catch (Exception e) {
            log.info("无效token: {}", e.getMessage());
            return 1; //token无效
        }

    }


    /**
     * 从token中获取用户信息
     *
     * @param token token字符串
     * @return 用户信息
     */
    public static WxUser getWxUserFromToken(String token) {
        //获取token验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build();

        try {
            //verify是解析token的方法 返回解析后的token对象
            DecodedJWT verify = jwtVerifier.verify(token);
            //判断token是否过期
            if (verify.getExpiresAt().before(new Date())) {
                throw new ParseTokenException("token已过期,请重新登录");
            }
            //从token中获取用户信息
            String userJson = verify.getClaim("user").asString();
            //使用谷歌gson来反序列化json 注意java9+，
            // Gson 在处理 java.time.LocalDateTime 类型时无法访问其私有字段导致的。
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
                    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) ->
                            LocalDateTime.parse(json.getAsString()))
                    .create();

            return gson.fromJson(userJson, WxUser.class);
        } catch (Exception e) {
            log.info("token无效: {}", e.getMessage());
            //json有错误抛出异常 终止代码向下执行
            return null;
        }

    }


}
