package com.makro.config;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class) ;

    @Pointcut("@annotation(com.makro.config.LogFilter)")
    public void logPointCut (){

    }
    @Around("logPointCut()")
    public Object around (ProceedingJoinPoint point) throws Throwable {
        Object result = null ;
        try{
            // 执行方法
            result = point.proceed();
            // 保存请求日志
            saveRequestLog(point);
        } catch (Exception e){
            // 保存异常日志
            saveExceptionLog(point,e.getMessage());
        }
        return result;
    }

    /**
     * 捕获异常:/ by zero
     * 请求路径:http://localhost:8011/saveExceptionLog
     * 请求方法:saveExceptionLog
     * 模块描述:保存异常日志
     * 请求参数:["cicada"]
     */
    private void saveExceptionLog (ProceedingJoinPoint point,String exeMsg){
        LOGGER.info("捕获异常:"+exeMsg);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LOGGER.info("请求路径:"+request.getRequestURL());
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        LOGGER.info("请求方法:"+method.getName());
        // 获取方法上LogFilter注解
        LogFilter logFilter = method.getAnnotation(LogFilter.class);
        String value = logFilter.value() ;
        LOGGER.info("模块描述:"+value);
        Object[] args = point.getArgs();
        LOGGER.info("请求参数:"+ JSONObject.toJSONString(args));
    }

    /**
     * 请求路径:http://localhost:8011/saveRequestLog
     * 请求方法:saveRequestLog
     * 模块描述:保存请求日志
     * 请求参数:["cicada"]
     */
    private void saveRequestLog (ProceedingJoinPoint point){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LOGGER.info("请求路径:"+request.getRequestURL());
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        LOGGER.info("请求方法:"+method.getName());
        // 获取方法上LogFilter注解
        LogFilter logFilter = method.getAnnotation(LogFilter.class);
        String value = logFilter.value() ;
        LOGGER.info("模块描述:"+value);
        Object[] args = point.getArgs();
        LOGGER.info("请求参数:"+ JSONObject.toJSONString(args));
    }
}
