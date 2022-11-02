## Ribbon

Ribbon是客户端负载均衡组件，在spring cloud中 提供微服务是用ribbon和openfeign
进行调用的。openfeign其实也是基于ribbon进行封装的。
微服务之间的调用往往被称为“客户端负载均衡”，这是因为在Eureka的机制中，任何微服务都是Eureka的“客户端”。

### 1、引入ribbon

只要引入了eureka 会自动引入ribbon，所以不需要额外的引入

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

### 2、配置负载均衡调用器

```java

@Configuration
class Config {
    @LoadBalanced // 负载均衡
    @Bean // 注入bean
    public RestTemplate getRestTemp() {
        return new RestTemplate();
    }
}
```

在 `spring-cloud-eureka-client` 服务上增加测试的方法

```java

@RestController
class EurekaClientController {
    
    // 其他方法.....
    
    
    @Value("${server.port}")
    private Integer port;
    
    @GetMapping("/port")
    public String getPort() {
        return "port:" + port;
    }

}
```

配置ribbon调用

```java
@RestController
class InfoController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试负载均衡调用
     * 调用spring-cloud-eureka-client服务 这个服务有多个实例
     * 返回的是被调用服务的端口信息
     */
    @GetMapping("/balance")
    public String info() {
        // url的名称为 http://被调用的服务名(spring-cloud-eureka-client)+具体的url(/port)
        String url = "http://spring-cloud-eureka-client/port";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        return entity.getBody();
    }
}
```