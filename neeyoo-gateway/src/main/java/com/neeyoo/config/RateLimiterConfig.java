package com.neeyoo.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description: 限流配置
 */
@Configuration
public class RateLimiterConfig {

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 接口限流操作
     */
//    @Bean(name = "apiKeyResolver")
//    public KeyResolver apiKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getPath().value());
//
//    }
    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: ip限流操作
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 使用这种方式限流，请求路径中必须携带userId参数。
     */
//    @Bean
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//    }

}
