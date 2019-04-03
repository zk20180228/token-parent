package cn.ctcc.token.module01.config.resttemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zk
 * @Date: 2019/3/21 10:29
 * @Description: @Configuration
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 使用ribbon进行负载均衡-->常用的模式：ribbon+RestTemplate,默认：线性负载均衡策略(RoundRobinRule)，即轮询调用
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){

        return  new RestTemplate();
    }

}
