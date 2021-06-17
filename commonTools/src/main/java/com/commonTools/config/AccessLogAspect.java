package com.commonTools.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AccessLogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postControllerMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getControllerMethod() {
    }

    @Around("postControllerMethod() || getControllerMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder logMessage = new StringBuilder();
        Object[] args = pjp.getArgs();
        Object target = pjp.getTarget();
        String serviceName = target.getClass().getName();
        logMessage.append(" serviceName: ").append(serviceName);

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String methodName = methodSignature.getMethod().getName();
        logMessage.append("|").append(" methodName: ").append(methodName);

        ObjectMapper objectMapper = new ObjectMapper();

        //追加输入参数
        logMessage.append("|").append(" request params:  ");
        try
        {
            logMessage.append(objectMapper.writeValueAsString(args));
        }
        catch (Exception e)
        {
            logMessage.append("{}");
        }

        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            long costTime = System.currentTimeMillis() - startTime;
            logMessage.append(" | ").append(" cost-time: [").append(costTime).append("]");
            log.error(logMessage.toString(), e);
            throw e;
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            logMessage.append(" | ").append(" response:  [").append(objectMapper.writeValueAsString(result)).append("]");
            logMessage.append(" | ").append(" cost-time: [").append(costTime).append("]");
            log.info(logMessage.toString());
        }

        return result;
    }
}
