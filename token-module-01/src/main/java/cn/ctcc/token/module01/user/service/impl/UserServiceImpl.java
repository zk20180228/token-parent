package cn.ctcc.token.module01.user.service.impl;

import cn.ctcc.token.common.beans.User;
import cn.ctcc.token.common.interfaces.service.UserService;
import cn.ctcc.token.module01.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author zk
 * @since 2019-04-02
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
