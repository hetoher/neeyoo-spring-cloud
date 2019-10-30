package com.neeyoo.controller;

import com.neeyoo.annotation.ImportantLog;
import com.neeyoo.exception.ConsumerException;
import com.neeyoo.redis.RedisManager;
import com.neeyoo.request.TestRequest;
import com.neeyoo.service.TestService;
import com.neeyoo.util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description:
 */
@Slf4j
@RestController
@Api(tags = "consumer-接口文档")
public class TestController {

    @Value("${spring.datasource.url}")
    private String url;

    @Autowired
    private TestService testService;
    @Autowired
    private RedisManager redisManager;

    @ApiOperation(value = "consumer-测试接口(get)", notes = "测试接口")
    @GetMapping("/test")
    public ApiResult test() throws ConsumerException {
        return ApiResult.ok(url, "成功");
    }

    @ApiOperation(value = "consumer-测试接口(get)", notes = "测试接口")
    @ApiImplicitParam(name = "testString", value = "测试字符串", required = true, dataType = "String")
    @GetMapping("/getTest")
    public ApiResult getTest(@RequestParam String testString) throws ConsumerException {
        log.info("get接口测试");
        if (StringUtils.isEmpty(testString)) {
            throw new ConsumerException(-99, "测试失败");
        }
        return ApiResult.ok(testString, "成功");
    }

    @ImportantLog("测试重要日志")
    @ApiOperation(value = "consumer-测试接口(post)", notes = "测试接口")
    @PostMapping("/postTest")
    public ApiResult postTest(@Validated @RequestBody TestRequest testRequest) throws ConsumerException {
        log.info("post接口测试");
        if (testRequest.getName().equals("yes")) {
            throw new ConsumerException(-99, "post测试失败");
        }
        return ApiResult.ok(testRequest, "成功");
    }

    @ApiOperation(value = "consumer-redis缓存测试(get)", notes = "测试接口")
    @ApiImplicitParam(name = "phone", value = "测试手机号", required = true, dataType = "String")
    @GetMapping("/getTestCode")
    public ApiResult getTestCode(@RequestParam String phone) throws ConsumerException {
        log.info("测试缓存");
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        redisManager.hset("SMS_CODE::" + phone, "code", code);
        redisManager.expire("SMS_CODE::" + phone, 60 * 30);
        return ApiResult.ok(code, "成功");
    }

    @ApiOperation(value = "consumer-redis缓存查询(get)", notes = "测试接口")
    @ApiImplicitParam(name = "phone", value = "redis缓存查询", required = true, dataType = "String")
    @GetMapping("/queryCode")
    public ApiResult queryCode(@RequestParam String phone) throws ConsumerException {
        log.info("测试查询缓存");
        Object code = redisManager.hget("SMS_CODE::" + phone, "code");
        return ApiResult.ok(code, "成功");
    }

}
