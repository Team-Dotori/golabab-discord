package com.dotori.golababdiscord.domain.discord.advice;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.exception.AlreadyVoteException;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.user.exception.UserNotEnrolledException;
import com.dotori.golababdiscord.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class VoteAdvice {
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
    private final UserService userService;

    @Around("execution(void com.dotori.golababdiscord.domain.discord.listeners.handler.VoteHandler.handleEvent(*))")
    public void around(ProceedingJoinPoint pjp) {
        GuildMessageReactionAddEvent event = (GuildMessageReactionAddEvent) pjp.getArgs()[0];

        PrivateChannel channel = event.getUser().openPrivateChannel().complete();
        ReceiverDto receiver = new ReceiverDto(channel);

        boolean isThrowException = true;
        try {
            pjp.proceed();
            isThrowException = false;
        } catch (UserNotEnrolledException e) {
            MessageDto message = messageViews.generateRequestAuthorizeMessage();
            messageSenderService.sendMessage(receiver, message);
        } catch (PermissionDeniedException e) {
            MessageDto message = messageViews.generatePermissionDeniedMessage(
                    userService.getUserDto(event.getUser()).getPermission(), e.getFeature());
            messageSenderService.sendMessage(receiver, message);
        } catch (AlreadyVoteException e) {
            MessageDto message = messageViews.generateAlreadyVoteMessage();
            messageSenderService.sendMessage(receiver, message);
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
