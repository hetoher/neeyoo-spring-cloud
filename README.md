# neeyoo-spring-cloud
Spring Cloud 项目

## 项目主要使用(学习中...)
- Spring Boot 2.1.3.RELEASE
- Spring Cloud Gateway 网关
- spring Cloud Alibaba Nacos 服务发现与配置中心
- JWT 用于token验证
- Redis redis缓存
- Mybatis 持久层框架
- RabbitMQ 消息队列

// 阿里哨兵

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
    - neeyoo-seckill 秒杀模块

- 20190916 整合了gateway + swagger2实现api文档的统一管理, 增加了对swagger相关url权限的过滤, 下一步完善鉴权模块
- 接下来对jwt token验证的编写
- 配置中心
    - 配置中心相关请参考 [https://blog.csdn.net/educast/article/details/90446604]
    - 具体参考Spring Cloud Alibaba Nacos Config [https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-docs/src/main/asciidoc-zh/nacos-config.adoc]

# 生产环境使用Nacos，Nacos服务需要至少部署三个节点，再加上MySQL主备


gateway port: 15000
auth port: 15001
consumer port: 15002


-- 随记录

spring cloud gateway ServerCodecConfigurer 问题: 包冲突
<dependency>
	<groupId>neeyoo.common</groupId>
	<artifactId>neeyoo-common</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<exclusions>
	    <exclusion>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-web</artifactId>
	    </exclusion>
	    <exclusion>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-webflux</artifactId>
	    </exclusion>
	</exclusions>
</dependency>


#### ModelMapper实体转DTO
例子:
ModelMapper modelMapper = new ModelMapper();
//单个转换
ProductInfo productInfo = service.getOne();
ProductInfoDto dto = modelMapper.map(productInfo,ProductInfoDto.calss);
//list转换
List<ProductInfo> productInfos =service.getList();
List<ProductInfoDto> productInfoDtos = modelMapper.map(productInfos, new TypeToken<List<ProductInfoDto>>() {}





-- 狼道 鬼谷子 羊皮卷 人性的弱点 墨菲定律 九型人格 说话心理学 读心术 微表情心理学 人际交往心理学














































