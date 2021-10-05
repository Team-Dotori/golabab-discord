package com.dotori.golababdiscord.domain.logger.advice;

import com.dotori.golababdiscord.domain.logger.annotation.CommandRunner;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Slf4j
public class CommandLogger {
    @Around("@annotation(com.dotori.golababdiscord.domain.logger.annotation.CommandRunner)")
    public void loggingCommand(ProceedingJoinPoint pjp) {
        MethodSignature method = (MethodSignature) pjp.getSignature();
        CommandRunner annotation = method.getMethod().getAnnotation(CommandRunner.class);

        if(!annotation.requireLog()) return;

        log.info("test!");
    }
}
