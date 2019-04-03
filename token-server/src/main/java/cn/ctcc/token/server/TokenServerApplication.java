package cn.ctcc.token.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
* @Author: zk
* @Date: 2019/4/1 17:44
* @Description:
*/
@EnableDiscoveryClient
@SpringBootApplication
public class TokenServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenServerApplication.class, args);
    }

}
