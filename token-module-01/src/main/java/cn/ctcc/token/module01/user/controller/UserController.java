package cn.ctcc.token.module01.user.controller;

import cn.ctcc.token.common.beans.User;
import cn.ctcc.token.common.interfaces.service.UserService;
import cn.ctcc.token.common.util.BackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zk
 * @Date: 2019/4/2 16:08
 * @Description: 用户控制器
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;


    @RequestMapping(value = "/getUserById",produces = {MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE})
    public BackResult getUserById(String id){

        try {
            if(StringUtils.isBlank(id)){
                return BackResult.build(500,"用户id不能为空！");
            }
            User user = userService.getById(id);
            return BackResult.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试！");
    }





}
