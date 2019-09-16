package com.neeyoo.aspect;

import com.neeyoo.util.RequestHolderUtils;
import com.neeyoo.util.StringUtils;
import com.neeyoo.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description:
 */
@Component
@Aspect
@Slf4j
public class ImportantLogAspect {


    private long currentTime = 0L;

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 配置切入点
     */
    @Pointcut("@annotation(com.neeyoo.annotation.ImportantLog)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 配置环绕通知,使用在方法logPointcut()上注册的切入点
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        params.append("}");
        long costTime = System.currentTimeMillis() - currentTime;
        log.error("INFO> methodName：{}, params：{}, costTime：{}, ip:{}",
                methodName,
                params.toString(),
                costTime,
                StringUtils.getIP(RequestHolderUtils.getHttpServletRequest()));
        return result;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 配置异常通知
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        // 这里处理抛出异常
        currentTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        params.append("}");
        long costTime = System.currentTimeMillis() - currentTime;
        log.error("ERROR> methodName：{}, params：{}, costTime：{}, ip:{}",
                methodName,
                params.toString(),
                costTime,
                StringUtils.getIP(RequestHolderUtils.getHttpServletRequest()));
    }

}
