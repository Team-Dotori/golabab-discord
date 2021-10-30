package com.dotori.golababdiscord.domain.command.advice;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.command.exception.UnknownCommandException;
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

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//Command로직에서 발생한 예외를 처리하는 Aspect 클래스
@Component
@Aspect
@RequiredArgsConstructor
public class CommandAdvice {
    private final MessageSenderService messageSenderService;//메시지 전송 서비스
    private final MessageViews messageViews;//메시지 뷰
    private final UserService userService;//유저 서비스

    //예외가 발생하면 처리하는 메소드
    @Around("execution(void com.dotori.golababdiscord.domain.discord.listeners.handler.CommandHandler.handleEvent(*))")
    public void around(ProceedingJoinPoint pjp) {
        MessageReceivedEvent event = (MessageReceivedEvent) pjp.getArgs()[0];//메서드 인자로 주어진 메세지 송신 이벤트
        try {//예외가 발생시 처리를 위해 실행문을 try 로 감싼다
            pjp.proceed();//메서드 실행
        } catch (UnknownCommandException ignored) {//커맨드가 아닌 채팅일경우
        } catch (WrongArgumentException e) {//잘못된 인자를 입력한 경우
            ReceiverDto receiver = new ReceiverDto(event.getChannel());//메세지를 송신할 채널을 설정한다
            MessageDto message = messageViews.generateWrongCommandUsageMessage(WrongCommandUsageType.WRONG_ARGUMENT, e.getArgs(), e.getUsage());//잘못된 인자를 입력한 경우 전송할 메시지를 생성한다

            messageSenderService.sendMessage(receiver, message);
        } catch (ArgumentNotFoundException e) {//인자를 찾을 수 없을경우
            ReceiverDto receiver = new ReceiverDto(event.getChannel());//메세지를 송신할 채널을 설정한다
            MessageDto message = messageViews.generateArgumentNotFoundMessage();//인자를 찾을 수 없을때 전송할 메시지를 생성한다

            messageSenderService.sendMessage(receiver, message);//메세지를 전송한다
        } catch (DepartmentNotFoundException e) {//소속정보를 찾을 수 없을 경우
            ReceiverDto receiver = new ReceiverDto(event.getChannel());//메세지를 송신할 채널을 설정한다
            MessageDto message = messageViews.generateAuthorizeFailureMessage(FailureReason.DOMAIN_IS_NOT_SCHOOL_DOMAIN);//소속정보를 찾을 수 없는 경우 전송할 메시지를 생성한다

            messageSenderService.sendMessage(receiver, message);//메세지를 전송한다
        } catch (PermissionDeniedException e) {//권한이 없을 경우
            User user = event.getAuthor();//유저 정보
            PrivateChannel channel = user.openPrivateChannel().complete();//유저의 비공개 채널을 연다
            ReceiverDto receiver = new ReceiverDto(channel);//메세지를 송신할 채널을 설정한다
            MessageDto message = messageViews.generatePermissionDeniedMessage(//권한 부족으로 인한 오류메세지를 생성한다
                    userService.getUserDto(user).getPermission(), e.getFeature());//사용자의 권한과 사용자가 사용한 기능을 받아옴
            messageSenderService.sendMessage(receiver, message);//메세지를 전송한다
        } catch (Throwable throwable) {//그이외의 오류가 발생할경우
            throwable.printStackTrace();//오류를 출력한다
        }
    }
}
