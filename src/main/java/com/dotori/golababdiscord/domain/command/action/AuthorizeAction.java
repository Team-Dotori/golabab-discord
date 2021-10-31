package com.dotori.golababdiscord.domain.command.action;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.UnValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.command.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.infra.service.MailService;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.ChannelDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class AuthorizeAction {
    private final MailService mailService;//메일 서비스
    private final AuthorizeService authorizeService;//사용자 인증 서비스
    private final MessageViews messageViews;//메시지 뷰
    private final SpringTemplateEngine templateEngine;//템플릿 엔진

    public void authorize(Argument argument, UserDto author, ChannelDto channel) {
        if(!checkArgs(argument)) throw new WrongArgumentException(argument, "소고야 인증 <실명> <이메일>");//잘못된 인자를 받았을 경우 예외를 발생시킨다

        UnValidatedUserDto unValidatedUser = getUnValidatedUser(author, argument);//사용자 정보를 받아온다
        DomainValidatedUserDto domainValidatedUser = validateDomain(unValidatedUser, argument);//이메일 도메인을 인증한다
        startValidateAuthorizeEmail(domainValidatedUser, channel);//이메일 인증을 시작한다. (입력한 이메일로 인증 메일을 보낸다)
    }

    //디스코드 유저와 인증명령어의 인자로 UnValidatedUserDto 를 구성하는 메서드
    private UnValidatedUserDto getUnValidatedUser(UserDto user, Argument argument) {
        String name = argument.getArgument();//사용자 이름
        return new UnValidatedUserDto(user.getId(), name);//UnValidatedUserDto 를 구성하여 반환한다
    }

    //UnValidateUserDto 와 인증명령어의 인자로 입력한 메일의 도메인이 학교도메인인지 검증하고, 학교도메인일 경우 DomainValidatedUserDto 를 반환하는 메서드
    private DomainValidatedUserDto validateDomain(UnValidatedUserDto unValidatedUser, Argument argument) {
        String email = argument.getNext().getArgument();//사용자가 입력한 이메일
        return authorizeService.validateDomain(unValidatedUser, email);//인증 서비스를 통해 도메인을 검증하고, DomainValidateUserDto 를 반환한다 (검증실패시 예외 발생)
    }

    //이메일 인증을 수행하는 메서드
    private void startValidateAuthorizeEmail(DomainValidatedUserDto domainValidatedUser, ChannelDto channel) {
        sendAuthorizeMail(domainValidatedUser);//인증 메일을 보낸다
        sendRequestCheckMailMessage(domainValidatedUser, channel);//인증 메일 확인을 요청하는 메세지를 보낸다
    }

    //인증메일을 보내는 메서드
    private void sendAuthorizeMail(DomainValidatedUserDto domainValidatedUser) {
        String authorizeLink = authorizeService.generateAuthorizeLink(domainValidatedUser);//인증링크를 생성한다

        String name = domainValidatedUser.getName();//사용자 이름
        String email = domainValidatedUser.getEmail();//사용자가 입력한 이메일
        String subject = getAuthorizeMailSubject();//메일 제목
        String content = getAuthorizeMailContentByLink(authorizeLink, name);//메일 내용

        mailService.sendHtmlEmail(email, subject, content);//인증메일을 송신한다
    }

    //인증메일 제목을 가져온다
    private String getAuthorizeMailSubject() {
        return "[소고봇] SW마이스터고 학생이신가요?";
    }

    //인증메일 내용을 가져온다
    private String getAuthorizeMailContentByLink(String authorizeLink, String name) {
        Context context = new Context();//Thymeleaf를 통해 html 형식의 메일 내용을 생성하기 위해 Context 객체를 생성한다
        //Thymeleaf 에 보낼 모델을 구성한다
        context.setVariable("link", authorizeLink);//인증링크
        context.setVariable("name", name);//사용자 이름

        return templateEngine.process("discord/authorize/authorize-mail", context);//템플릿 엔진을 통해 html 형식의 메일 내용을 가져와 반환한다
    }

    //인증 메일 확인을 요청하는 메세지를 보내는 메서드
    private void sendRequestCheckMailMessage(DomainValidatedUserDto domainValidatedUser, ChannelDto channel) {
        MessageDto legacyMessage = messageViews.generateMailSentMessage(domainValidatedUser);

        //TODO 2021.10.30 메세지 로직 개편 후 삭제 JeeInho
        EmbedMessageDto message = MessageViews.getEmbedMessageByLegacyMessageDto(legacyMessage);//불러온 레거시 메세지를 현재 스펙의 메세지로 치환한다(Message 로직 리펙터링 후 제거예정)
        chat(message, channel.getId());//인증 메일 확인 요청 메세지를 송신한다
    }

    private boolean checkArgs(Argument argument) {
        /*인자의 개수를 확인한다*/
        if(!(argument != null && argument.getNext() != null))//인자의 개수가 맞지 않을 경우
            return false;//false를 반환한다
        /* 인자의 형식을 확인한다 */
        try {
            String email = argument.getNext().getArgument();//이메일을 받아온다
            InternetAddress emailAddress = new InternetAddress(email);//이메일을 인증할 수 있는 형태로 변환한다
            emailAddress.validate();//이메일을 인증한다
        } catch (AddressException e) {//알맞은 이메일 형식이 아닐경우
            return false;//false 를 반환한다
        }
        return true; //인자의 개수가 맞고 이메일이 알맞은 형식일 경우 true를 반환한다
    }
}
