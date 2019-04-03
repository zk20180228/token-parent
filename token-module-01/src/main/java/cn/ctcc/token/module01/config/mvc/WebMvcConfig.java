package cn.ctcc.token.module01.config.mvc;

import cn.ctcc.token.module01.interceptors.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zk
 * @Date: 2019/3/1 15:28
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .maxAge(3600);
    }


    @Bean
    public TokenInterceptor tokenInterceptor(){

        return new TokenInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**");
    }



}
