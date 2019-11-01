package com.neeyoo.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.neeyoo.config.Constant;
import com.neeyoo.config.ReturnCodeEnum;
import com.neeyoo.dao.ConsumerDAO;
import com.neeyoo.domain.NeeYooConsumer;
import com.neeyoo.dto.LoginSuccessDTO;
import com.neeyoo.exception.AuthorityException;
import com.neeyoo.redis.RedisManager;
import com.neeyoo.request.LoginRequest;
import com.neeyoo.request.RefreshTokenRequest;
import com.neeyoo.service.ConsumerService;
import com.neeyoo.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Value("${jwt.secret.key}")
    private String secretKey; // jwt生成密钥
    @Value("${jwt.token.expire-time}")
    private long tokenExpireTime; // token过期时间
    @Value("${jwt.token.refresh.expire-time}")
    private long tokenRefreshExpireTime; // refreshToken过期时间
    @Value("${jwt.token.key-format}")
    private String tokenKeyFormat; // token 存储key
    @Value("${jwt.token.refresh.key-format}")
    private String tokenRefreshKeyFormat; // refreshToken 存储key
    @Value("${jwt.blacklist.key-format}")
    private String blacklistKeyFormat; // token黑名单 存储key 登陆
    @Value("${jwt.blacklist.expire-time}")
    private long blacklistExpireTime; // blacklist过期时间

    @Autowired
    private RedisManager redisManager;
    @Autowired
    private ConsumerDAO consumerDAO;

    /**
     * Create by NeeYoo.
     * Create on 2019/10/29.
     * Description: 用户登录
     */
    @Override
    @Transactional
    public LoginSuccessDTO login(LoginRequest request) throws AuthorityException {
        String registrationId;
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        NeeYooConsumer userInfo = consumerDAO.findByUserPhone(request.getUserPhone());
        String tokenKey = String.format(tokenKeyFormat, request.getUserPhone()); // token key
        String refreshTokenKey = String.format(tokenRefreshKeyFormat, request.getUserPhone()); // refreshToken key
        Object redisToken = redisManager.hget(tokenKey, Constant.AUTHORIZE_TOKEN); // 在更新token之前获取
        Object redisRefreshToken = redisManager.hget(refreshTokenKey, Constant.REFRESH_TOKEN); // 在更新token之前获取
        LoginSuccessDTO loginSuccess = new LoginSuccessDTO();
        if (null != userInfo) {
            registrationId = userInfo.getRegistrationId();
            String encryptionPassword = EncryptionPasswordUtil.encryptionPassword(request.getPassword(), userInfo.getSalt());
            if (!encryptionPassword.equals(userInfo.getPassword())) {
                throw new AuthorityException(ReturnCodeEnum.USER_ACCOUNT_ERROR.getCode(), "账户或密码错误");
            }
            if (null != redisToken) {
                try {
                    DecodedJWT decodedJWT = JwtTokenUtil.decodedJWT(algorithm, String.valueOf(redisToken));
                    if (!(decodedJWT.getClaim(Constant.DEVICE_ID).asString().equals(request.getDeviceId())) && !StringUtils.isEmpty(registrationId)) {
                        PushUtil.accountRemoteLogin(registrationId); // 提示异地登录
                    }
                    // 旧token添加到黑名单
                    redisManager.hset(String.format(blacklistKeyFormat, redisToken), "", ""); // 设置token
                    redisManager.expire(String.format(blacklistKeyFormat, redisToken), blacklistExpireTime); // 设置过期时间
                    // 旧refreshToken添加到黑名单
                    redisManager.hset(String.format(blacklistKeyFormat, redisRefreshToken), "", ""); // 设置token
                    redisManager.expire(String.format(blacklistKeyFormat, redisRefreshToken), blacklistExpireTime); // 设置过期时间
                } catch (Exception e) {
                    log.error(ThrowableUtils.getStackTrace(e));
                }
            }
            consumerDAO.updateRegistrationId(request.getUserPhone(), request.getRegistrationId(), System.currentTimeMillis()); // 登录成功更新极光id和登录时间

            // 生成JWT token
            String token = JwtTokenUtil.generateToken(request.getUserPhone(), request.getDeviceId(), secretKey, tokenExpireTime);
            redisManager.hset(tokenKey, Constant.AUTHORIZE_TOKEN, token); // 设置token
            redisManager.expire(tokenKey, tokenExpireTime); //token设置过期时间

            // 生成refreshToken
            String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
            redisManager.hset(refreshTokenKey, Constant.REFRESH_TOKEN, refreshToken); // 设置token
            redisManager.expire(refreshTokenKey, tokenRefreshExpireTime); // refreshToken设置过期时间

            // 返回结果
            loginSuccess.setUserPhone(request.getUserPhone());
            loginSuccess.setAuthorization(token);
            loginSuccess.setRefreshToken(refreshToken);
        } else {
            throw new AuthorityException(ReturnCodeEnum.USER_NOT_EXISTS.getCode(), "请检查账号输入是否正确");
        }
        return loginSuccess;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/10/31.
     * Description: 刷新token
     */
    @Override
    public Map<String, Object> refreshToken(RefreshTokenRequest request) throws AuthorityException {
        Map<String, Object> resultMap = new HashMap<>();
        NeeYooConsumer neeYooConsumer = consumerDAO.findByUserId(request.getUserId());
        if (null == neeYooConsumer){
            throw new AuthorityException(ReturnCodeEnum.LOGIN_FAIL_INVALID.getCode(), "请重新登录");
        }
        String tokenKey = String.format(tokenKeyFormat, neeYooConsumer.getUserPhone()); // token key
        String refreshTokenKey = String.format(tokenRefreshKeyFormat, neeYooConsumer.getUserPhone()); // refreshToken key
        Object redisToken = redisManager.hget(tokenKey, Constant.AUTHORIZE_TOKEN); // 在更新token之前获取
        Object redisRefreshToken = redisManager.hget(refreshTokenKey, Constant.REFRESH_TOKEN); // 在更新token之前获取
        if (null != redisRefreshToken && redisRefreshToken.equals(request.getRefreshToken())){
            // 生成JWT token
            String token = JwtTokenUtil.generateToken(neeYooConsumer.getUserPhone(), request.getDeviceId(), secretKey, tokenExpireTime);
            redisManager.hset(tokenKey, Constant.AUTHORIZE_TOKEN, token); // 设置token
            redisManager.expire(tokenKey, tokenExpireTime); //token设置过期时间
            // 旧token添加到黑名单
            redisManager.hset(String.format(blacklistKeyFormat, redisToken), "", ""); // 设置token
            redisManager.expire(String.format(blacklistKeyFormat, redisToken), blacklistExpireTime); // 设置过期时间
            resultMap.put("token", token);
            return resultMap;
        } else {
            throw new AuthorityException(ReturnCodeEnum.LOGIN_FAIL_INVALID.getCode(), "请重新登录");
        }
    }
}
