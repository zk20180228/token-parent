package cn.ctcc.token.module02.config.exception;

import cn.ctcc.token.common.util.BackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zk
 * @Date: 2019/3/1 16:11
 * @Description: 全局异常处理
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionController implements ErrorController {


    @RequestMapping("/error")
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public BackResult exceptionMsg(Exception e){

        return BackResult.build(500,e.getMessage());
    }

    /**
     *
     * 为了覆盖系统自身的/error异常
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
