package cn.ctcc.token.module02;

import cn.ctcc.token.common.util.BackResult;
import cn.ctcc.token.common.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenModule02ApplicationTests {


    @Value("${TOKEN_MODULE_01_APPLICATIONNAME}")
    private  String  TOKEN_MODULE_01_APPLICATIONNAME;

    @Value("${TOKEN_MODULE_01_USER_GETUSERBYID}")
    private String TOKEN_MODULE_01_USER_GETUSERBYID;

    @Value("${TOKEN_SERVER_APPLICATIONNAME}")
    private String TOKEN_SERVER_APPLICATIONNAME;

    @Value("${TOKEN_SERVER_TOKEN_GETTOKEN}")
    private String TOKEN_SERVER_TOKEN_GETTOKEN;


    @Resource
    private RestTemplate restTemplate;

    @Before
    public void testBefore(){
        System.out.println(TOKEN_MODULE_01_APPLICATIONNAME);
        System.out.println(TOKEN_MODULE_01_USER_GETUSERBYID);
        System.out.println(restTemplate);
    }



    @Test
    public void testGetUserByIdInNoToken() {

        String id="37";
        String url="http://"+TOKEN_MODULE_01_APPLICATIONNAME+TOKEN_MODULE_01_USER_GETUSERBYID+"?id="+id;
        //这里必须用String接受,否则会报一个类型不确定的异常
        String str = restTemplate.getForObject(url, String.class);
        log.info("--------------------------------"+str);
    }


    @Test
    public void testGetUserByIdWithToken() throws Exception {

        String id="37";
        String getTokenUrl="http://"+TOKEN_SERVER_APPLICATIONNAME+TOKEN_SERVER_TOKEN_GETTOKEN+"?userName=honry&password=root";
        //这里必须用String接受,否则会报一个类型不确定的异常
        String tokenStr = restTemplate.getForObject(getTokenUrl, String.class);
        BackResult tokenBack = JSONUtils.fromJson(tokenStr, BackResult.class);
        String token = (String) tokenBack.getData();

        String url="http://"+TOKEN_MODULE_01_APPLICATIONNAME+TOKEN_MODULE_01_USER_GETUSERBYID+"?id="+id+"&token="+token;
        //这里必须用String接受,否则会报一个类型不确定的异常
        String str = restTemplate.getForObject(url, String.class);
        log.info("--------------------------------"+str);
    }

}
