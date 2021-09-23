package com.dotori.golababdiscord.domain.logger;

import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class CommandLogger {
    private final BotProperty botProperty;

    @Around("execution(boolean com.dotori.golababdiscord.domain.discord.command.RootCommand.execute(*, *, *, *))")
    public boolean arround(ProceedingJoinPoint pjp) throws Throwable {
        boolean result = (boolean)pjp.proceed();
        if(!result) return false;

        Object[] args = pjp.getArgs();
        User user = (User) args[1];
        MessageChannel channel = (MessageChannel) args[2];
        String commandArgs = (String) args[3];

        log.info("Command \"{} {}\" is executed by {} at #{}",
                botProperty.getCommandPrefix(), commandArgs,
                user.getName(),
                channel.getName());

        return true;
    }

}
