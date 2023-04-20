package com.ls.pms.web.aop;

import com.alibaba.fastjson.JSONObject;
import com.ls.pms.web.common.utils.RequestUtil;
import com.ls.pms.web.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * web 请求响应的日志
 *
 * @author yejd
 * @date 2023-04-19 17:27
 * @description
 */
@Aspect
@Component
@Slf4j(topic = "web")
public class WebLogAop {

    private static ThreadLocal<Long> ACCESS_TIME = new ThreadLocal<>();

    private static final int MAX_PRINT_SIZE = 512;

    @Pointcut("execution(public * com.ls.pms.web.controller..*.*(..))")
    public void accessPointcut() {

    }

    @Before("accessPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ACCESS_TIME.set(System.currentTimeMillis());
        HttpServletRequest request =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String userAgent = request.getHeader("user-agent");
        String params = getParamsString(joinPoint.getArgs());
        String ip = RequestUtil.getIpAddr(request);
        String uri = request.getRequestURI();
        log.info("请求信息：user={} ,ip={} ,uri={} ,userAgent={}, params={}", UserThreadLocal.get(), ip, uri, userAgent,
            params);
    }

    @AfterReturning(pointcut = "accessPointcut()", returning = "retValue")
    public void afterReturning(JoinPoint point, Object retValue) {
        String fullMethodName = point.getTarget().getClass().getSimpleName() + "." + point.getSignature().getName();
        String result;
        if (retValue != null && retValue.toString().length() >= MAX_PRINT_SIZE) {
            result = StringUtils.substring(retValue.toString(), 0, MAX_PRINT_SIZE);
        } else {
            result = JSONObject.toJSONString(retValue);
        }

        log.info("返回结果：{} 耗时={}毫秒，响应结果：retValue={}", fullMethodName,
            System.currentTimeMillis() - ACCESS_TIME.get(), result);
    }

    private String getParamsString(Object[] args) {
        if (args.length == 0) {
            return "";
        } else {
            //过滤掉spring的类，如BindResult
            List<Object> argList =
                Arrays.stream(args).filter(e -> e == null || !e.getClass().getName().startsWith("org.springframework"))
                    .collect(Collectors.toList());
            List<String> params = argList.stream().map(JSONObject::toJSONString).collect(Collectors.toList());
            return StringUtils.join(params, ",");
        }
    }
}
