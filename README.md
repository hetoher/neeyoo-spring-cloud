# neeyoo-spring-cloud
Spring Cloud 项目

- Spring Boot 2.1.3.RELEASE
- Spring Cloud Gateway 网关
- Spring Cloud Alibaba Nacos 服务发现与配置中心(v1.1.4)
- Spring Cloud Sleuth + zipkin 链路追踪
- 监控中心Spring Boot Admin + Turbine
- JWT 用于token验证
- Redis redis缓存
- Mybatis 持久层框架
- RabbitMQ 消息队列

// 阿里哨兵

## 项目结构
- neeyoo-logistics-api
    - neeyoo-common 公用模块
        - annotation 自定注解
        - exception 项目统一异常的处理
        - config 公用配置
        - redis redis缓存相关配置
        - util 系统通用工具类
    - neeyoo-consumer 用户模块 (port: 15002)[包拆分,可以了解下]
    	- consumer-server 对外服务接口
    	- consumer-client 定义interface提供给其他微服务
    	- consumer-common 定义公用代码
    - neeyoo-gateway 网关模块(port: 15000)
    - neeyoo-authority 鉴权模块(port: 15001)
    - neeyoo-seckill 秒杀模块

- 整合了gateway + swagger2实现api文档的统一管理, 增加了对swagger相关url权限的过滤
- Nacos配置中心 / 配置中心
    - 总的来说，Apollo和Nacos相对于Spring Cloud Config的生态支持更广，在配置管理流程上做的更好。Apollo相对于Nacos在配置管理做的更加全面，不过使用起来也要麻烦一些。
    - Nacos使用起来相对比较简洁，在对性能要求比较高的大规模场景更适合。 生产环境使用Nacos，Nacos服务需要至少部署三个节点，再加上MySQL主备

    此外，Nacos除了提供配置中心的功能，还提供了动态服务发现、服务共享与管理的功能，降低了服务化改造过程中的难度。
    - 配置中心相关请参考 [https://blog.csdn.net/educast/article/details/90446604]
    - 具体参考Spring Cloud Alibaba Nacos Config [https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-docs/src/main/asciidoc-zh/nacos-config.adoc]
    - 配置文件参考放在neeyoo-tool模块中
    - 使用mysql持久化增加以下配置
        spring.datasource.platform=mysql
        db.num=1
        db.url.0=jdbc:mysql://localhost:3306/neeyoo?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
        db.user=root
        db.password=root

    一直报错: failed to req API:/nacos/v1/ns/api/clientBeat 说明nacos挂掉了
    循环的方式在控制台打印日志(将nacos心跳日志打印级别提高): 在bootstrap.yml配置中增加
    logging:
      level:
        com:
          alibaba:
            nacos:
              client:
                naming: error


- zipkin链路追踪
    在 Spring Boot 2.0 版本之后，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包 下载地址: [https://zipkin.io/pages/quickstart.html]
    默认端口号启动zipkin服务
    java –jar zipkin.jar 默认端口号; 9411
    访问地址:http://192.168.18.220:9411
    在需要使用的微服务pom文件中增加依赖
    <!--开启zipkin服务链路跟踪-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>
    使用zipkin时疯狂报错: ERROR com.alibaba.nacos.client.naming - [] [] [NA] failed to update serviceName: 127.0.0.1
    解决参考地址: [http://www.itmuch.com/spring-cloud-alibaba/spring-cloud-alibaba-zipkin-nacos-exception/]
    原因: Spring Cloud把 http://localhost:9411/ 当作了服务发现组件里面的服务名称；
        于是，Nacos Client尝试从Nacos Server寻找一个名为 localhost:9411 的服务;
        这个服务根本不存在啊，于是就疯狂报异常（因为Nacos Client本地定时任务，刷新本地服务发现缓存）
    解决办法: 让Spring Cloud 正确识别 http://localhost:9411/ ，当成一个URL，而不要当做服务名。把Zipkin Server注册到Nacos
    修改配置为:
    spring:
      zipkin:
        base-url: http://localhost:9411/
        discovery-client-enabled: false
    实际去测试的时候发现, 还是会继续报错
    参见: org.springframework.cloud.sleuth.zipkin2.sender.ZipkinRestTemplateSenderConfigur
    修改配置为:
    spring:
      zipkin:
        base-url: http://localhost:9411/
        discoveryClientEnabled: false
    相关的Issue在：https://github.com/spring-cloud/spring-cloud-sleuth/issues/1376
    解决的Pul Request在：https://github.com/spring-cloud/spring-cloud-sleuth/pull/1379 ，代码已经合并了，在 Spring Cloud Greenwich SR3 版本中会修正！
    简单总结一下：
    如果你使用的是Greenwich SR3之前的版本，务必使用 spring.zipkin.discoveryClientEnabled = false ，否则配置不生效！！
    如果你使用的是Greenwich SR3及更高版本，可使用 discovery-client-enabled 或者 discoveryClientEnabled 。

    其它:
    使用Elasticsearch作为Zipkin Server的存储。
    下载es: https://www.elastic.co/products/elasticsearch
    启动: 解压
        cd elasticsearch-6.5.3/bin
        ./elasticsearch
    STORAGE_TYPE=elasticsearch ES_HOSTS=localhost:9200 java -jar zipkin-server-2.11.3-exec.jar
    其中：STORAGE_TYPE 指定存储类型是啥；ES_HOSTS 指定你的Elasticsearch地址列表，多个用 , 分隔。还可指定其他环境变量，详见：
    https://github.com/openzipkin/zipkin/tree/master/zipkin-server#elasticsearch-storage







-- 随记录

- spring cloud gateway ServerCodecConfigurer 问题: 包冲突
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


- ModelMapper实体转DTO
    例子:
    ModelMapper modelMapper = new ModelMapper();
    //单个转换
    ProductInfo productInfo = service.getOne();
    ProductInfoDto dto = modelMapper.map(productInfo,ProductInfoDto.calss);
    //list转换
    List<ProductInfo> productInfos =service.getList();
    List<ProductInfoDto> productInfoDtos = modelMapper.map(productInfos, new TypeToken<List<ProductInfoDto>>() {}





-- 狼道 鬼谷子 羊皮卷 人性的弱点 墨菲定律 九型人格 说话心理学 读心术 微表情心理学 人际交往心理学














































