package cn.ctcc.token.module02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
* @Author: zk
* @Date: 2019/4/9 16:19
* @Description: 模拟消费者模块
*
*/
@EnableDiscoveryClient
@EnableHystrix
@SpringBootApplication
public class TokenModule02Application {

    public static void main(String[] args) {
        SpringApplication.run(TokenModule02Application.class, args);
    }

}
