package com.lyy.wxloginlearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.lyy.wxloginlearn.entity.dto.AccessKeyResponseDTO;
import com.lyy.wxloginlearn.entity.dto.WxLoginDTO;
import com.lyy.wxloginlearn.entity.dto.WxPhoneResponseDTO;
import com.lyy.wxloginlearn.entity.dto.WxRequestByLoginCodeDTO;
import com.lyy.wxloginlearn.entity.pojo.WxUser;
import com.lyy.wxloginlearn.exception.LoginCodeErrorException;
import com.lyy.wxloginlearn.mapper.WxUserMapper;
import com.lyy.wxloginlearn.service.IWxUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyy.wxloginlearn.utils.JwtUtils;
import com.lyy.wxloginlearn.utils.WxRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 微信小程序用户表 服务实现类
 * </p>
 *
 * @author YueYang
 * @since 2025-10-25
 */
@Slf4j
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {

    @Autowired
    private WxRequest wxRequest;

    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    @Qualifier("MyGson")
    private Gson GSON;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 处理微信小程序用户登录
     *
     * @param wxLoginDTO 微信小程序用户登录DTO
     * @return 微信小程序用户登录结果
     */
    @Override
    public WxUser handleWxLogin(WxLoginDTO wxLoginDTO) {

        //1. 像微信后端发送请求获取openid
        if (wxLoginDTO.getLoginCode() == null) {
            throw new IllegalArgumentException("登录凭证码不能为空");
        }
        //发送请求 获取openId
        WxRequestByLoginCodeDTO loginRes = wxRequest.getLoginParamsByLoginCode(wxLoginDTO.getLoginCode());
        if (loginRes.getOpenid() == null) {
            //通过loginCode获取响应内容openid为空 表示获取失败
            throw new LoginCodeErrorException(loginRes.getErrmsg());
        }

        //2. 获取access_token 先从redis当中拿 没有再从微信后端获取
        String access_token = (String) redisTemplate.opsForValue().get("access_token");
        if (access_token == null) {
            //redis当中没有access_key从微信后端获取
            AccessKeyResponseDTO accessTokenRes = wxRequest.getAccessToken();
            if (accessTokenRes.getAccess_token() == null) {
                throw new LoginCodeErrorException("获取access_token失败，请稍后再试试");
            }
            //将access_token存储到数据库中 设置两个小时过期
            access_token = accessTokenRes.getAccess_token();
            redisTemplate.opsForValue().set("access_token", access_token, accessTokenRes.getExpires_in(), TimeUnit.SECONDS);
        }
        log.info("获取到的access_token: {}", access_token);


        //3. 将openid和 phoneCode 以及access_token发送给微信后端获取手机号
        //发送请求 获取手机号
        if (wxLoginDTO.getPhoneCode() == null) {
            throw new IllegalArgumentException("获取手机号凭证码不能为空");
        }
        //TODO 1 注意这里没法获取手机号 请求啥的可能有问题跟别人对一对
        /*WxPhoneResponseDTO phoneRes = wxRequest.WxRequestGetPhoneNumber(wxLoginDTO.getPhoneCode(), loginRes.getOpenid(), access_token);
        log.info("获取手机号响应内容: {}", phoneRes);*/
        //TODO 2 注意这里成功获取到手机号可能没有 errorcode这个参数
        /*if (phoneRes.getErrcode() != 0) {
            //通过phoneCode获取响应内容errcode不为0 表示获取失败
            throw new LoginCodeErrorException(phoneRes.getErrmsg());
        }*/


        //4. 查询数据库 看该手机号是否存在
        //TODO 3 注意这里手机号我们还没填写 所以这里查询数据库是根据openid查询
        /* WxUser wxUserByPhone = wxUserMapper.selectOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getPhone, phoneRes.getPhone_info().getPurePhoneNumber()));*/

        //TODO 4 先不对手机号进行判断
     /*   if (wxUserByPhone == null) {
            //注意这个时候还没有存储token 因为还没获取用户的id
            //数据库不存在该对象，创建token 创建新用户
            WxUser wxUser = WxUser.builder()
                    .openid(loginRes.getOpenid())
                    //.phone(phoneRes.getPhone_info().getPurePhoneNumber())
                    .avatar(wxLoginDTO.getAvatarUrl())
                    .nickname(wxLoginDTO.getNickname())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();

            //插入数据库当中 插入成功后 wxUser当中有了用户id
            int insertRow = wxUserMapper.insert(wxUser);
            if (insertRow > 0) log.info("插入用户成功: {}", wxUser);
            //创建token
            String token = JwtUtils.createToken(GSON.toJson(wxUser));
            wxUser.setToken(token);
            //根据用户id更新记录 添加token
            Integer userId = wxUser.getId();
            int row = wxUserMapper.updateById(wxUser);
            if (row > 0) log.info("更新用户token成功: userId={}, token={}", userId, token);
            return wxUser;
        } else {
            //数据库存在该对象 说明是老用户 直接创建token返回
            String token = JwtUtils.createToken(GSON.toJson(wxUserByPhone));
            wxUserByPhone.setToken(token);
            //根据用户id更新记录 添加token
            int row = wxUserMapper.updateById(wxUserByPhone);
            if (row > 0) log.info("用户token成功: userId={}, token={}", wxUserByPhone.getId(), token);
            return wxUserByPhone;
        }*/

        //TODO 5 我们获取到openid后直接插入数据即可
        WxUser wxUser = WxUser.builder()
                .openid(loginRes.getOpenid())
                //.phone(phoneRes.getPhone_info().getPurePhoneNumber())
                .avatar(wxLoginDTO.getAvatarUrl())
                .nickname(wxLoginDTO.getNickname())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        //插入数据库当中 插入成功后 wxUser当中有了用户id
        int insertRow = wxUserMapper.insert(wxUser);
        if (insertRow > 0) log.info("插入用户成功: {}", wxUser);
        //创建token
        String token = JwtUtils.createToken(GSON.toJson(wxUser));
        wxUser.setToken(token);
        //根据用户id更新记录 添加token
        Integer userId = wxUser.getId();
        int row = wxUserMapper.updateById(wxUser);
        if (row > 0) log.info("更新用户token成功: userId={}, token={}", userId, token);
        return wxUser;

    }

    /**
     * 获取微信小程序用户信息
     *
     * @param id 微信小程序用户id
     * @return 微信小程序用户信息
     */
    @Override
    public WxUser getUserInfo(Long id) {
        return wxUserMapper.selectById(id);
    }
}
