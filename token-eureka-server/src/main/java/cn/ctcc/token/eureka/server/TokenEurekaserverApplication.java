package cn.ctcc.token.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: zk
 * @Date: 2019/4/1 16:06
 * @Description: token项目的eureka注册中心
 */
@EnableEurekaServer
@SpringBootApplication
public class TokenEurekaserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenEurekaserverApplication.class, args);
    }

}
