package com.liao.spring.cloud.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class, args);
    }
}

@Configuration
class Config {
    @LoadBalanced // 负载均衡
    @Bean // 注入bean
    public RestTemplate getRestTemp() {
        return new RestTemplate();
    }
}

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

    @GetMapping("/sentinel")
    public String test(){
        String url = "http://spring-cloud-eureka-client/test";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        return entity.getBody();
    }
}




