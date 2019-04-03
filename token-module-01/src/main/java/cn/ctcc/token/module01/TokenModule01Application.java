package cn.ctcc.token.module01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @Author: zk
 * @Date: 2019/4/2 16:06
 * @Description: 模拟用户微服务
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TokenModule01Application {

    public static void main(String[] args) {
        SpringApplication.run(TokenModule01Application.class, args);
    }

}
