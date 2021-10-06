package com.dotori.golababdiscord.domain.logger.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthorizeLogger {
    @AfterReturning("execution(String com.dotori.golababdiscord.domain.authorize.controller.AuthorizeController.authorize(..))")
    public void log(JoinPoint joinPoint) {
        String token = (String) joinPoint.getArgs()[0];
        log.info("Authorize that payload \"{}\" is success", token.split("\\.")[1]);
    }
}
