package com.dotori.golababdiscord.domain.discord.advice;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.discord.exception.ArgumentNotFoundException;
import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import lombok.RequiredArgsConstructor;
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

    @Around("execution(void com.dotori.golababdiscord.domain.discord.listeners.CommandListener.onMessageReceived(*))")
    public void around(ProceedingJoinPoint pjp) {
        MessageReceivedEvent event = (MessageReceivedEvent) pjp.getArgs()[0];
        try {
            pjp.proceed();
        } catch (WrongArgumentException e) {
            event.getChannel().sendMessage("알수없는 인자 : \"" + e.getArgs().split(" ")[0] + "\"").complete();
            ReceiverDto receiver = new ReceiverDto(event.getChannel());
            MessageDto message = messageViews.generateWrongCommandUsageMessage(WrongCommandUsageType.WRONG_ARGUMENT, e.getArgs(), e.getUsage());
            sendBadRequestMessage();
        } catch (ArgumentNotFoundException e) {
            event.getChannel().sendMessage("인자가 존재하지 않음").complete();
            ReceiverDto receiver = new ReceiverDto(event.getChannel());
            MessageDto message = messageViews.generateArgumentNotFoundMessage();
            sendBadRequestMessage();
        } catch (DepartmentNotFoundException e) {
            ReceiverDto receiver = new ReceiverDto(event.getChannel());
            MessageDto message = messageViews.generateAuthorizeFailureMessage(FailureReason.DOMAIN_IS_NOT_SCHOOL_DOMAIN);

            messageSenderService.sendMessage(receiver, message);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    //TODO view 사용해서구현하기
    private void sendBadRequestMessage() {
    }
}
