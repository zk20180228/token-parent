package cn.ctcc.token.common.constant;

/**
 * @Author: zk
 * @Date: 2019/4/2 15:01
 * @Description: 常量类
 * @Modified:
 * @version: V1.0
 */
public class SysConstant {

    /**
     * token的前缀
     */
    public static final String  REDIS_TOKEN_PREFIX="REDIS_TOKEN";

    /**
     * token的有效时长
     */
    public static final Long REDIS_TOKEN_EXPIRE_TIME=1800L;

    /**
     * token有效时间小于300s时,重置token的有效时间，并刷新token的值
     */
    public static final Long REDIS_TOKEN_REFRESH_TIME=300L;

}
