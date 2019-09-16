package com.neeyoo.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description: swagger文档配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Bean(name = "neeyoo-consumer")
    public Docket createRestApi() {
        //=====添加head参数start============================
        ParameterBuilder parameter1 = new ParameterBuilder();
        ParameterBuilder parameter2 = new ParameterBuilder();
        ParameterBuilder parameter3 = new ParameterBuilder();
        ParameterBuilder parameter4 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        parameter1.name("Authorization").description("Authorization令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameter2.name("userId").description("用户Id").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameter3.name("userPhone").description("用户电话").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameter4.name("deviceId").description("设备号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(parameter1.build());
        pars.add(parameter2.build());
        pars.add(parameter3.build());
        pars.add(parameter4.build());
        // =========添加head参数end===================
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable) // 是否激活swagger文档
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.neeyoo"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars).groupName("neeyoo-consumer");

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("consumer-接口文档")
                .description("接口文档-用户服务")
                .termsOfServiceUrl("http://localhost:8080/doc.html")
                .version("1.0")
                .build();
    }

}
