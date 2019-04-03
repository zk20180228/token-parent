package cn.ctcc.token.module01.interceptors;

import cn.ctcc.token.common.beans.User;
import cn.ctcc.token.common.util.BackResult;
import cn.ctcc.token.common.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2019/4/2 16:24
 * @Description: 校验token是否有效的拦截器
 * @Modified:
 * @version: V1.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Value("${TOKEN_SERVER_APPLICATIONNAME}")
    private String TOKEN_SERVER_APPLICATIONNAME;

    @Value("${TOKEN_SERVER_TOKEN_CHECKTOKEN}")
    private String TOKEN_SERVER_TOKEN_CHECKTOKEN;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getParameter("token");
        if(StringUtils.isBlank(token)){

            BackResult result = BackResult.build(500, "access not allowed! please get token first");
            String json = JSONUtils.toJson(result);
            response.getWriter().write(json);
            return false;
        }

        String url="http://"+TOKEN_SERVER_APPLICATIONNAME+TOKEN_SERVER_TOKEN_CHECKTOKEN+"?token="+token;
        BackResult<User> backResult = restTemplate.getForObject(url, BackResult.class);
       if(backResult!=null) {
           if (backResult.getStatus() == 200) {
               //返回的user实体是个map
               Map userMap = (Map) backResult.getData();
               if(userMap!=null){
                    return true;
                }
           }
       }

       BackResult result = BackResult.build(500, "access not allowed! token is invalid");
       String json = JSONUtils.toJson(result);
       response.getWriter().write(json);
       return false;
    }
}
