package com.liao.spring.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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