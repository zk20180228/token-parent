package cn.ctcc.token.common.base.hystrix;

import cn.ctcc.token.common.util.BackResult;

/**
 * @Author: zk
 * @Date: 2019/4/10 14:44
 * @Description: 提供公共的fallback方法
 * @Modified:
 * @version: V1.0
 */
public class BaseHystrixCommandFallback {



    /**
     * @Author: zk
     * @Date: 2019/4/10 14:09
     * @Param
     * @Return: cn.ctcc.token.common.util.BackResult
     * @Throws
     * @Description:
     *              适用于返回BackResult的所有Controller中的方法，
     *              有了Hystrix后,controller可以不用再try...catch,
     *              Hystrix可以声明为捕捉所有运行时异常，统一返回当前方法的返回值：
     *              @HystrixCommand(defaultFallback = "fallbackForController",raiseHystrixExceptions = {HystrixException.RUNTIME_EXCEPTION})
     *
     *              注意:默认的fallback方法可以不带参数，但是返回值一定要和所作用的方法的返回值一致
     *
     */
    public BackResult fallbackForController(){

        return BackResult.build(500,"服务故障，请稍后访问！");
    }


}
