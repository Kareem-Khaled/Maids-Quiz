package com.maids.salesmanagement.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.maids.salesmanagement.service.*.*(..))")
    public void logServiceMethodsBefore(JoinPoint joinPoint) {
        logger.info("Before executing service method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.maids.salesmanagement.service.*.*(..))", returning = "result")
    public void logServiceMethodAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("After returning from service method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "execution(* com.maids.salesmanagement.service.*.*(..))", throwing = "exception")
    public void logServiceMethodAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception occurred in service method: {}", joinPoint.getSignature().toShortString(), exception);
    }
}
