package cn.ctcc.token.module02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TokenModule02Application {

    public static void main(String[] args) {
        SpringApplication.run(TokenModule02Application.class, args);
    }

}
