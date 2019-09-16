package com.neeyoo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description: 拦截器. 统计某个服务的响应时间(使用GlobalFilter, 不需要再路由时指定)
 * GlobalFilter的使用 1.加@Component注解  2.Spring Config 中配置这个@Bean
 */
@Component
@Slf4j
public class ElapsedFilter implements GlobalFilter, Ordered {

    @Value("${auth.skip.otherUrls}")
    private String[] skipOtherUrls;

    private static final String ELAPSED_START_TIME = "elapsedTimeStart";

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: Override GatewayFilter的方法, 做某服务时间响应记录
     */
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        serverWebExchange.getAttributes().put(ELAPSED_START_TIME, System.currentTimeMillis());
        return gatewayFilterChain.filter(serverWebExchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = serverWebExchange.getAttribute(ELAPSED_START_TIME);
                    Long executeTime = 0L;
                    boolean flag = true;
                    if (startTime != null) {
                        executeTime = System.currentTimeMillis() - startTime;
                    }
                    String path = serverWebExchange.getRequest().getURI().getPath();
                    for (String skipOtherUrl : skipOtherUrls) { // 不做时间响应记录
                        if (Pattern.compile(skipOtherUrl).matcher(path).find()) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        log.info(String.format("Method:{%s} Ip:{%s} Host:{%s} Path:{%s} Query:{%s} Cost:{%s ms}",
                                serverWebExchange.getRequest().getMethod().name(),
                                serverWebExchange.getRequest().getRemoteAddress().getAddress().getHostAddress(),
                                serverWebExchange.getRequest().getURI().getHost(),
                                serverWebExchange.getRequest().getURI().getPath(),
                                serverWebExchange.getRequest().getQueryParams(),
                                executeTime));
                    }
                })
        );
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 过滤器设定优先级别的，值越大则优先级越低
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
