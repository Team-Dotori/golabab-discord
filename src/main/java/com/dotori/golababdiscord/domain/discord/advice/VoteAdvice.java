package com.dotori.golababdiscord.domain.discord.advice;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.user.exception.UserNotEnrolledException;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class VoteAdvice {
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
    @Around("execution(void com.dotori.golababdiscord.domain.discord.listeners.VoteListener.onGuildMessageReactionAdd(*))")
    public void around(ProceedingJoinPoint pjp) {
        GuildMessageReactionAddEvent event = (GuildMessageReactionAddEvent) pjp.getArgs()[0];
        try {
            pjp.proceed();
        } catch (UserNotEnrolledException e) {
            PrivateChannel channel = event.retrieveUser().complete().openPrivateChannel().complete();

            ReceiverDto receiver = new ReceiverDto(channel);
            MessageDto message = messageViews.generateRequestAuthorizeMessage();

            messageSenderService.sendMessage(receiver, message);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
