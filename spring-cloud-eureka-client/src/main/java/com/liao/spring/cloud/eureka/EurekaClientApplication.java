package com.liao.spring.cloud.eureka;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
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

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/port")
    public String getPort() {
        return "port:" + port;
    }

    @Autowired
    private HelloService service;

    @GetMapping("/test")
    public String test() {

        return service.testSentinel()+service.data();
    }

}


@Service
class HelloService {

    @SentinelResource(value = "test")
    public String testSentinel() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "sentinel";
    }


    @SentinelResource(value = "data")
    public String data(){
        return "data";
    }
}