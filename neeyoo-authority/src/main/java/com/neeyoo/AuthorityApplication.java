package com.neeyoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description: 鉴权微服务
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }
}
