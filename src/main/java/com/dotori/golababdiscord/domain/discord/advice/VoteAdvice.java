package com.dotori.golababdiscord.domain.discord.advice;

import com.dotori.golababdiscord.domain.discord.exception.AlreadyVoteException;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.message.MessageFactory;
import com.dotori.golababdiscord.domain.user.exception.UserNotEnrolledException;
import com.dotori.golababdiscord.domain.user.service.UserService;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@Aspect
@RequiredArgsConstructor
public class VoteAdvice {
    private final MessageFactory messageFactory;
    private final UserService userService;

    @Around("execution(void com.dotori.golababdiscord.domain.discord.listeners.handler.VoteHandler.handleEvent(*))")
    public void around(ProceedingJoinPoint pjp) {
        GuildMessageReactionAddEvent event = (GuildMessageReactionAddEvent) pjp.getArgs()[0];

        PrivateChannel channel = event.getUser().openPrivateChannel().complete();

        boolean isThrowException = true;
        try {
            pjp.proceed();
            isThrowException = false;
        } catch (UserNotEnrolledException e) {
            EmbedMessageDto message = messageFactory.generateRequestAuthorizeMessage();
            chat(message, channel.getIdLong());
        } catch (PermissionDeniedException e) {
            EmbedMessageDto message = messageFactory.generatePermissionDeniedMessage(
                    userService.getUserDto(event.getUser().getIdLong()).getPermission(), e.getFeature());
            chat(message, channel.getIdLong());
        } catch (AlreadyVoteException e) {
            EmbedMessageDto message = messageFactory.generateAlreadyVoteMessage();
            chat(message, channel.getIdLong());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if(isThrowException)
            cancelEvent(event);
    }

    private void cancelEvent(@NotNull GuildMessageReactionAddEvent event) {
        event.getReaction().removeReaction(
                event.retrieveUser().complete()
        ).complete();
    }
}
