## 服务注册中心

### 1、搭建单机版服务注册中心

项目名称 `spring-cloud-eureka-service` \

#### 1.1、pom.xml

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    <!--如果是JDK11要加入-->
    <dependency>
        <groupId>org.glassfish.jaxb</groupId>
        <artifactId>jaxb-runtime</artifactId>
    </dependency>
</dependencies>
```

#### 1.2 启动类

```java

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServiceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
```

#### 1.3 配置文件

```yaml
spring:
  application:
    name: spring-cloud-eureka-service
server:
  port: 8761 # 服务端口
eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false # 服务本身就是注册中心，所以不需要进行注册
    fetchRegistry: false # 取消服务获取
    serviceUrl: # 服务注册的地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

#### 1.4 运行

在浏览器中输入 `http://127.0.0.1:8761/` 如果可以看到这个界面，就证明配置成功

![eureka-service-img01](https://ghproxy.com/https://raw.githubusercontent.com/liaoxianfu/blogimg/main/data/20221031173008.png)

### 02、服务注册

项目名称 spring-cloud-eureka-client

服务注册中心的目的就是为了监控各个服务的状态，所以我们需要将服务注册到服务注册中心上。

pom.xml

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--引入eureka-client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

测试业务代码

```java

@SpringBootApplication
public class EurekaClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}

@RestController
class EurekaClientController {
    /**
     * 测试能正常访问服务，返回一个简单的字符串
     *
     * @return EurekaClient
     */
    @GetMapping("/")
    public String hello() {
        return "EurekaClient";
    }
}
```

配置

```yaml
spring:
  application:
    name: spring-cloud-eureka-client

server:
  port: 4001

eureka:
  client:
    service-url:
      # defaultZone 必须使用驼峰命名法，不能支持匈牙利命名 无法正常转换
      defaultZone: http://localhost:8761/eureka/
  instance:
    hostname: localhost

```

