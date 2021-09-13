package com.dotori.golababdiscord.domain.discord.advice;

import com.dotori.golababdiscord.domain.discord.exception.ArgumentNotFoundException;
import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CommandAdvice {
    @Around("execution(void com.dotori.golababdiscord.domain.discord.listeners.CommandListener.onMessageReceived(*))")
    public void around(ProceedingJoinPoint pjp) {
        MessageReceivedEvent event = (MessageReceivedEvent) pjp.getArgs()[0];
        try {
            pjp.proceed();
        } catch (WrongArgumentException e) {
            event.getChannel().sendMessage("알수없는 인자 : \"" + e.getArgs().split(" ")[0] + "\"").complete();
            sendBadRequestMessage();
        } catch (ArgumentNotFoundException e) {
            event.getChannel().sendMessage("인자가 존재하지 않음").complete();
            sendBadRequestMessage();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    //TODO view 사용해서구현하기
    private void sendBadRequestMessage() {
    }
}
