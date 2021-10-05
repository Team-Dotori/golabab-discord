package com.dotori.golababdiscord.domain.command.advice;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.discord.exception.ArgumentNotFoundException;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class CommandAdvice {
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
    private final UserService userService;

    @Around("execution(void com.dotori.golababdiscord.domain.discord.listeners.handler.CommandHandler.handleEvent(*))")
    public void around(ProceedingJoinPoint pjp) {
        MessageReceivedEvent event = (MessageReceivedEvent) pjp.getArgs()[0];
        try {
            pjp.proceed();
        } catch (WrongArgumentException e) {
            ReceiverDto receiver = new ReceiverDto(event.getChannel());
            MessageDto message = messageViews.generateWrongCommandUsageMessage(WrongCommandUsageType.WRONG_ARGUMENT, e.getArgs(), e.getUsage());

            messageSenderService.sendMessage(receiver, message);
        } catch (ArgumentNotFoundException e) {
            ReceiverDto receiver = new ReceiverDto(event.getChannel());
            MessageDto message = messageViews.generateArgumentNotFoundMessage();

            messageSenderService.sendMessage(receiver, message);
        } catch (DepartmentNotFoundException e) {
            ReceiverDto receiver = new ReceiverDto(event.getChannel());
            MessageDto message = messageViews.generateAuthorizeFailureMessage(FailureReason.DOMAIN_IS_NOT_SCHOOL_DOMAIN);

            messageSenderService.sendMessage(receiver, message);
        } catch (PermissionDeniedException e) {
            User user = event.getAuthor();
            PrivateChannel channel = user.openPrivateChannel().complete();
            ReceiverDto receiver = new ReceiverDto(channel);
            MessageDto message = messageViews.generatePermissionDeniedMessage(
                    userService.getUserDto(user).getPermission(), e.getFeature());
            messageSenderService.sendMessage(receiver, message);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
