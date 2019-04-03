package cn.ctcc.token.server.user.mapper;

import cn.ctcc.token.common.beans.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2019-04-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
