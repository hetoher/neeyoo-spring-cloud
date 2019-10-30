package com.neeyoo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description: 
 */
@Slf4j
@RestController
@Api(tags = "auth-接口文档")
public class TestController {

    @ApiOperation(value = "auth-测试接口", notes = "测试接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a", paramType = "path", value = "数字a", required = true, dataType = "path"),
            @ApiImplicitParam(name = "b", paramType = "path", value = "数字b", required = true, dataType = "path")
    })
    @GetMapping("/{a}/{b}")
    public Integer get(@PathVariable Integer a, @PathVariable Integer b) {
        log.info("auth文档访问");
        return a + b;
    }

}
