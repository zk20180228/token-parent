package cn.ctcc.tokenhystrixturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
* @Author: zk
* @Date: 2019/4/9 15:50
* @Description: hystrix-turbine:集群断路器状态监控
*/
@EnableDiscoveryClient
@EnableHystrixDashboard
@SpringBootApplication
public class TokenHystrixTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenHystrixTurbineApplication.class, args);
    }

}
