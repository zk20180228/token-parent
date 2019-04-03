package cn.ctcc.token.module02.user;

import cn.ctcc.token.common.util.BackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2019/4/3 10:56
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("/userTest")
public class UserTestController {

    @Value("${TOKEN_MODULE_01_APPLICATIONNAME}")
    private String TOKEN_MODULE_01_APPLICATIONNAME;

    @Value("${TOKEN_MODULE_01_USER_GETUSERBYID}")
    private String TOKEN_MODULE_01_USER_GETUSERBYID;

    @Resource
    private RestTemplate restTemplate;



    @RequestMapping("/testGetUserById")
    public BackResult testGetUserById(String id,String token) {

        if(StringUtils.isBlank(id)||StringUtils.isBlank(token)){
            return BackResult.build(500,"参数为空！");
        }

        try {
            String url="http://"+TOKEN_MODULE_01_APPLICATIONNAME+TOKEN_MODULE_01_USER_GETUSERBYID+"?id="+id+"&token="+token;
            BackResult result = restTemplate.getForObject(url, BackResult.class);
            Map userMap = (Map) result.getData();
            System.out.println(userMap);
            return result;
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试！");
    }

}
