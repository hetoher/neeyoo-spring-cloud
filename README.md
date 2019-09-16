# neeyoo-spring-cloud
Spring Cloud 项目

## 项目简介
Spring Boot 2.1.3.RELEASE
Spring Cloud Gateway 网关
Spring Cloud Config 配置中心
Spring Cloud Bus 消息总线
Spring Security security
JWT 用于token验证
Redis redis缓存
Mybatis 持久层框架
Alibaba Nacos 服务注册
RabbitMQ 消息队列

## 项目结构(一步一步学习完善中, 先这么构思)
- neeyoo-logistics-api
    - neeyoo-common 公用模块
        - annotation 自定注解
        - exception 项目统一异常的处理
        - config 公用配置
        - redis redis缓存相关配置
        - util 系统通用工具类
    - neeyoo-consumer 用户模块
    	- consumer-server 对外服务接口
    	- consumer-client 定义interface提供给其他微服务
    	- consumer-common 定义公用代码
    - neeyoo-gateway 网关模块
    	- gateway-client 
    	- gateway-server
    - neeyoo-authority 鉴权模块
    - neeyoo-config 配置中心消息总线 
