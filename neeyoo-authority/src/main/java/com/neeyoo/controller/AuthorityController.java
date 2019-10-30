package com.neeyoo.controller;

import com.neeyoo.exception.AuthorityException;
import com.neeyoo.redis.RedisManager;
import com.neeyoo.request.LoginRequest;
import com.neeyoo.service.ConsumerService;
import com.neeyoo.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description: 鉴权服务
 */
@Slf4j
@RestController
@Api(tags = {"鉴权服务"})
public class AuthorityController {

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
    private ConsumerService consumerService;

    /**
     * Create by NeeYoo.
     * Create on 2019/10/29.
     * Description: 登录授权
     */
    @ApiOperation(value = "登录授权", notes = "LoginRequest登录请求实体类")
    @PostMapping("/consumer/login")
    public ApiResult login(@Validated @RequestBody LoginRequest request) throws AuthorityException {
        // TODO 这里应该做前传过来的密码加密, 或者整个参数加密
        return ApiResult.ok(consumerService.login(request), "登录成功");
    }

    // TODO 刷新token
    // TODO 验证token
    // TODO 退出登录

}
