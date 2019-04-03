package cn.ctcc.token.module02.config.application;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2019/3/1 15:25
 * @Description: 通过applicationContext获取容器中的对象,几乎没啥用
 * @Modified:
 * @version: V1.0
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextUtils.applicationContext=applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的名称获取bean
     */
    public static Object getBeanByName(String name){

        return applicationContext.getBean(name);
    }

    /**
     * 根据根据class获取bean
     */
    public <T> T getBeanByClass(Class<T> clazz){

        return applicationContext.getBean(clazz);
    }

    /**
     * 根据bean的class来查找所有的对象（包括子类）
     */
    public static <T> Map<String,T> getBeansByClass(Class<T> clazz){

        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getCurrentRequest(){

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取HttpSession
     */
    public static HttpSession getCurrentSession(){

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        return servletRequestAttributes.getRequest().getSession();
    }

    /**
     * 获取完整的请求URL
     */
    public static String getRequestUrl(){
        return getRequestUrl(getCurrentRequest());
    }

    /**
     * 获取完整的请求URL
     */
    public static String getRequestUrl(HttpServletRequest request){
        //当前请求路径
        String currentUrl = request.getRequestURL().toString();
        //请求参数
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)){
            currentUrl = currentUrl + "?" + queryString;
        }
        System.out.println("---------------------------获取完整的请求URL:"+queryString);
        String result = "";
        try {
            result = URLEncoder.encode(currentUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            //ignore
        }

        return result;
    }

}
