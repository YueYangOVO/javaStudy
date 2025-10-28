package com.lyy.wxloginlearn.interceptor;

import com.lyy.wxloginlearn.exception.ParseTokenException;
import com.lyy.wxloginlearn.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author YueYang
 * Created on 2025/10/25 16:57
 * @version 1.0
 */
@Slf4j
@Component
public class RequestHandler implements HandlerInterceptor {

    //这里我们仅仅拦截请求放行前的操作

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 获取拦截请求路径 url是完整的请求路径， uri是资源路径
        String requestURI = request.getRequestURI();
        log.info("拦截到请求路径: {}", requestURI);

        //2. 获取请求头中的token
        String token = request.getHeader("token");
        //对token进行验证
        if (token == null || token.isEmpty()) {
            log.info("请求没有携带token，拒绝访问");
            response.setStatus(401); //未授权
            return false; //拒绝访问
        }
        //3.令牌存在 判断token是否合法
        int tokenStatus = JwtUtils.parseToken(token);
        if (tokenStatus == 0) {
            log.info("token过期");
            //token过期 重新登陆
            throw new ParseTokenException("token已过期 请重新登录");
        } else if (tokenStatus == 1) {
            //token无效 需要登录
            log.info("token无效");
            throw new ParseTokenException("无效的token 请重新登录");
        }

        //合法 放行
        return true;
    }
}
