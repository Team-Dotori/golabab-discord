package com.dotori.golababdiscord.domain.logger.advice;

import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class CommandLogger {
    private final BotProperty botProperty;

    @AfterReturning("execution(void com.dotori.golababdiscord.domain.command.node.RootCommand.executeRoot(..))")
    public void arround(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        MessageChannel channel = (MessageChannel) args[1];
        String commandArgs = (String) args[2];

        log.info("Command \"{} {}\" is executed by {} at #{}",
                botProperty.getCommandPrefix(), commandArgs,
                user.getName(),
                channel.getName());
    }

}
