package cn.ctcc.token.module02.user;

import cn.ctcc.token.common.base.hystrix.BaseHystrixCommandFallback;
import cn.ctcc.token.common.util.BackResult;
import cn.ctcc.token.common.util.JSONUtils;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2019/4/3 10:56
 * @Description:
 *              DefaultProperties指定默认的Hystrix的处理机制，
 *              raiseHystrixExceptions = {HystrixException.RUNTIME_EXCEPTION}捕捉所有运行时异常，
 *              因此，所有controller中不用在try...catch
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("/userTest")
@DefaultProperties(defaultFallback = "fallbackForController",raiseHystrixExceptions = {HystrixException.RUNTIME_EXCEPTION} )
public class UserTestController extends BaseHystrixCommandFallback {

    @Value("${TOKEN_MODULE_01_APPLICATIONNAME}")
    private String TOKEN_MODULE_01_APPLICATIONNAME;

    @Value("${TOKEN_MODULE_01_USER_GETUSERBYID}")
    private String TOKEN_MODULE_01_USER_GETUSERBYID;

    @Resource
    private RestTemplate restTemplate;


    @HystrixCommand
    @RequestMapping("/testGetUserById")
    public BackResult testGetUserById(String id,String token) throws Exception {
        if(StringUtils.isBlank(id)||StringUtils.isBlank(token)){
            return BackResult.build(500,"参数为空！");
        }

        String url="http://"+TOKEN_MODULE_01_APPLICATIONNAME+TOKEN_MODULE_01_USER_GETUSERBYID+"?id="+id+"&token="+token;
        String str= restTemplate.getForObject(url, String.class);
        BackResult result = JSONUtils.fromJson(str,BackResult.class);
        Map userMap = (Map) result.getData();
        System.out.println(userMap);

        return result;
    }



}
