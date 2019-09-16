package com.neeyoo.exception;

import com.neeyoo.util.ApiResult;
import com.neeyoo.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description: 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiResult handlerException(HttpServletRequest request, Exception e)
            throws Exception {
        log.error("exception：{}, requestMethod：{}, url：{}",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURL());
        log.error(ThrowableUtils.getStackTrace(e));
        return ApiResult.fail(-1, "处理失败, 请联系管理员加班");
    }

    @ResponseBody
    @ExceptionHandler(value = ConsumerException.class)
    public ApiResult handlerConsumerException(HttpServletRequest request, ConsumerException e)
            throws Exception {
        log.error("exception：{}, requestMethod：{}, url：{}",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURL());
        log.error(ThrowableUtils.getStackTrace(e));
        return ApiResult.fail(e.getCode(), e.getMessage());
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 捕获数据校验异常
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e)
            throws Exception {
        log.error("exception：{}, requestMethod：{}, url：{}",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURL());
//        log.error(ThrowableUtils.getStackTrace(e)); // 校验异常堆栈信息不打印
        return ApiResult.fail(-1, e.getBindingResult().getAllErrors().get(0).getDefaultMessage()); // 返回第一个验证错误信息
    }
}
