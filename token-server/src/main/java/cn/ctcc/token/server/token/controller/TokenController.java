package cn.ctcc.token.server.token.controller;

import cn.ctcc.token.common.beans.User;
import cn.ctcc.token.common.constant.SysConstant;
import cn.ctcc.token.common.interfaces.service.UserService;
import cn.ctcc.token.common.util.BackResult;
import cn.ctcc.token.common.util.CryptoUtils;
import cn.ctcc.token.common.util.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zk
 * @Date: 2019/4/2 10:10
 * @Description: 用于创建，获取，校验，注销token凭证
 * @Modified:
 * @version: V1.0
 */
@RequestMapping("/token")
@RestController
public class TokenController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * @Author: zk
     * @Date: 2019/4/2 11:27
     * @Param userName 用户名
              password 密码
     * @Return: cn.ctcc.token.common.util.BackResult<java.lang.Object>
     * @Throws
     * @Description: 根据用户名和密码获取token
     */
    @RequestMapping("/getToken")
    public BackResult<String> getToken(String userName,String password){

        try {
            if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
                return BackResult.build(500,"用户名或密码为空！");
            }

            String md5Pwd = CryptoUtils.encodeMD5(password);
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username",userName);
            userQueryWrapper.eq("password",md5Pwd);
            List<User> list = userService.list(userQueryWrapper);
            if(list!=null&&list.size()>0){
                User user=list.get(0);
                //去掉密码，安全存储
                user.setPassword(null);
                String userJson = JSONUtils.toJson(user);
                String uuid=UUID.randomUUID().toString().replace("-","");
                String token= SysConstant.REDIS_TOKEN_PREFIX+":"+uuid ;
                redisTemplate.opsForValue().set(token,userJson,SysConstant.REDIS_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);

                return BackResult.ok(uuid);
            }else{
                return BackResult.build(500,"用户名或密码不正确！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试！");

    }

    /**
     * @Author: zk
     * @Date: 2019/4/2 11:30
     * @Param token
     * @Return: cn.ctcc.token.common.util.BackResult<java.lang.Object>
     * @Throws
     * @Description: 根据token获取(校验)用户,若token有效,返回最新用户信息(不是redis缓存用户信息)，若无效，返回null，当token有效,生命小于5分钟，刷新token的生命为30分钟
     */
    @RequestMapping("/checkToken")
    public BackResult checkToken(String token){

        try {
            if(StringUtils.isBlank(token)){
                return BackResult.build(500,"用户凭证(token)为空！");
            }
            String mid=SysConstant.REDIS_TOKEN_PREFIX+":"+token;
            String str = redisTemplate.opsForValue().get(mid);

            if(StringUtils.isBlank(str)){
                return BackResult.build(500,"用户凭证(token)不正确或已过期，请重新获取！");
            }

            User user = JSONUtils.fromJson(str,User.class);
            //判断当前用户是否已被干掉
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username",user.getUsername());
            List<User> list = userService.list(userQueryWrapper);
            if(list!=null&&list.size()>0){
                //判断token有效时间是否小于5分钟，如果小于5分钟，重置token的有效时间为30分钟
                Long expire = redisTemplate.getExpire(mid, TimeUnit.SECONDS);
                User newUser = list.get(0);
                newUser.setPassword(null);
                String json = JSONUtils.toJson(newUser);
                if(expire<SysConstant.REDIS_TOKEN_REFRESH_TIME){

                    redisTemplate.opsForValue().set(mid,json,SysConstant.REDIS_TOKEN_EXPIRE_TIME,TimeUnit.SECONDS);
                }
                return BackResult.ok(newUser);
            }else{

                //用户被干掉，删除redis中的缓存
                Boolean flag = redisTemplate.delete(mid);
                if(flag) {
                    return BackResult.build(500, "用户凭证(token)已过期，请重新获取！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return BackResult.build(500,"网络故障，请稍后重试！");
    }

    /**
     * @Author: zk
     * @Date: 2019/4/2 11:35
     * @Param token
     * @Return: cn.ctcc.token.common.util.BackResult<java.lang.Object>
     * @Throws
     * @Description: 根据token删除token
     */
    @RequestMapping("/loginOutByToken")
    public BackResult<String> loginOutByToken(String token){

        try {
            if(StringUtils.isBlank(token)){
                return BackResult.build(500,"用户凭证(token)为空！");
            }

            String tt=SysConstant.REDIS_TOKEN_PREFIX+":"+token;
            Boolean flag = redisTemplate.delete(tt);
            if(flag) {
                return BackResult.ok();
            }else{
                return BackResult.build(500,"用户凭证(token)不正确！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试！");
    }


}
