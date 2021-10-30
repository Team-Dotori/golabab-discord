package com.dotori.golababdiscord.domain.command.function;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.UnValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.command.node.LeafCommand;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.infra.service.MailService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//사용자 인증 을 위한 커맨드 클래스
public class AuthorizeCommand extends LeafCommand {
    private final MailService mailService;//메일 서비스
    private final MessageSenderService messageSenderService;//메시지 전송 서비스
    private final AuthorizeService authorizeService;//사용자 인증 서비스
    private final MessageViews messageViews;//메시지 뷰
    private final SpringTemplateEngine templateEngine;//템플릿 엔진

    //기본생성자
    public AuthorizeCommand(String prefix, MailService mailService, MessageSenderService messageSenderService, AuthorizeService authorizeService, MessageViews messageViews, SpringTemplateEngine templateEngine) {
        super(prefix);
        this.mailService = mailService;
        this.messageSenderService = messageSenderService;
        this.authorizeService = authorizeService;
        this.messageViews = messageViews;
        this.templateEngine = templateEngine;
    }

    @Override//커맨드 실행시 실행되는 메서드
    protected void run(User user, MessageChannel channel, String args) {
        if(!checkArgs(args)) throw new WrongArgumentException(args, "소고야 인증 <실명> <이메일>");//잘못된 인자를 받았을 경우 예외를 발생시킨다

        UnValidatedUserDto unValidatedUser = getUnValidatedUser(user, args);//사용자 정보를 받아온다
        DomainValidatedUserDto domainValidatedUser = validateDomain(unValidatedUser, args);//이메일 도메인을 인증한다
        startValidateAuthorizeEmail(domainValidatedUser, channel);//이메일 인증을 시작한다. (입력한 이메일로 인증 메일을 보낸다)
    }

    //이메일 인증을 수행하는 메서드
    private void startValidateAuthorizeEmail(DomainValidatedUserDto domainValidatedUser, MessageChannel channel) {
        sendAuthorizeMail(domainValidatedUser);//인증 메일을 보낸다
        sendRequestCheckMailMessage(domainValidatedUser, channel);//인증 메일 확인을 요청하는 메세지를 보낸다
    }

    //인증 메일 확인을 요청하는 메세지를 보내는 메서드
    private void sendRequestCheckMailMessage(DomainValidatedUserDto domainValidatedUser, MessageChannel channel) {
        //인증 메일 확인 요청 메세지를 송신한다
        messageSenderService.sendMessage(
                new ReceiverDto(channel),//전송할 채널을 설정한다
                messageViews.generateMailSentMessage(domainValidatedUser));//인증 메일 확인 요청 메세지를 불러온다
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

    //디스코드 유저와 인증명령어의 인자로 UnValidatedUserDto 를 구성하는 메서드
    private UnValidatedUserDto getUnValidatedUser(User user, String args) {
        String name = getNameByArgs(args);//사용자 이름
        return new UnValidatedUserDto(user.getIdLong(), name);//UnValidatedUserDto 를 구성하여 반환한다
    }

    //UnValidateUserDto 와 인증명령어의 인자로 입력한 메일의 도메인이 학교도메인인지 검증하고, 학교도메인일 경우 DomainValidatedUserDto 를 반환하는 메서드
    private DomainValidatedUserDto validateDomain(UnValidatedUserDto unValidatedUser, String args) {
        String email = getEmailByArgs(args);//사용자가 입력한 이메일
        return authorizeService.validateDomain(unValidatedUser, email);//인증 서비스를 통해 도메인을 검증하고, DomainValidateUserDto 를 반환한다 (검증실패시 예외 발생)
    }

    //인자의 무결성을 검사하는 메서드
    private boolean checkArgs(String args) {
        return args != null &&//인자가 존재하며
                !args.equals("") &&//비어있지 않고
                (args.split(" ").length >= 2);//인자의 개수가 2개 이상일경우 true 를 반환한다 (이외에는 false 를 반환)
    }

    //인자를 통해 사용자 이름을 추출하는 메서드
    private String getNameByArgs(String args) {
        return args.split(" ")[0];
    }
    //인자를 통해 사용자가 입력한 이메일을 추출하는 메서드
    private String getEmailByArgs(String args) {
        return args.split(" ")[1];
    }
}
