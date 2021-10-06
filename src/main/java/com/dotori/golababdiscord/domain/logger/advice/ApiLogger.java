package com.dotori.golababdiscord.domain.logger.advice;

import com.dotori.golababdiscord.domain.logger.annotation.ApiEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class ApiLogger {
    @Around("@annotation(com.dotori.golababdiscord.domain.logger.annotation.ApiEntry)")
    public Object log(ProceedingJoinPoint pjp) {
        MethodSignature method = (MethodSignature) pjp.getSignature();
        ApiEntry annotation = method.getMethod().getAnnotation(ApiEntry.class);

        Object result;

        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

        log.info("\"{}\" called that action \"{}\"", annotation.apiName(), annotation.actionName());
        return result;
    }
}
