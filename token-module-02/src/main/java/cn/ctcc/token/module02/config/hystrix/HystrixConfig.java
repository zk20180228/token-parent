package cn.ctcc.token.module02.config.hystrix;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2019/4/9 17:00
 * @Description: hystrix.stream的相关配置
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class HystrixConfig {


    @Bean
    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet(){
        return new HystrixMetricsStreamServlet();
    }

    @Bean
    public ServletRegistrationBean registration(HystrixMetricsStreamServlet hystrixMetricsStreamServlet){

        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(hystrixMetricsStreamServlet);
        //是否启用该registrationBean
        registrationBean.setEnabled(true);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");

        return registrationBean;
    }
}
