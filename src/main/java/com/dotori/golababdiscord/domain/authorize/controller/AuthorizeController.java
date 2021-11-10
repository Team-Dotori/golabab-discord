package com.dotori.golababdiscord.domain.authorize.controller;

import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.enroll.exception.AlreadyEnrolledException;
import com.dotori.golababdiscord.domain.enroll.service.EnrollService;
import com.dotori.golababdiscord.domain.message.MessageFactory;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//인증작업을 수행하는 컨트롤러 클래스
@Controller
@RequiredArgsConstructor
public class AuthorizeController {
    private final AuthorizeService authorizeService;
    private final EnrollService enrollService;
    private final MessageFactory messageFactory;
    private final SogoBot sogoBot;

    //authorize 엔드포인트로 요청이 들어왔을경우 인증 및 가입로직을 수행한다
    @RequestMapping("/authorize")
    public String authorize(@RequestParam String token) {
        ValidatedUserDto validatedUser = authorizeService.validateAuthorizeLink(token);//token 을 검증함과 동시에, 검증완료된 유저(ValidatedUserDto)를 token 을 통해 가져온다
        enrollService.checkEnrollCondition(validatedUser.toUserDto());//이미 가입된 유저인지 검증한다
        sendAuthorizedMessage(validatedUser.getDiscordId());//가입조건을 충족하면, 가입완료 메시지를 보낸다
        enrollService.enroll(validatedUser.toUserDto());//가입로직을 수행한다
        return "discord/authorize/authorized";//가입완료 페이지로 이동한다
    }

    //가입조건을 충족하면, 가입완료 메시지를 보낸다
    private void sendAuthorizedMessage(Long discordId) {
        MessageChannel channel = sogoBot.getPrivateChannelByUserId(discordId);
        chat(messageFactory.generateAuthorizedMessage(), channel.getIdLong());
    }

    //TODO 2021.10.29 가입조건을 충족하지 못할경우 발생하는 EnrollFailureException 을 만들고 AlredyEnrolledException 을 해당 예외의 하위예외로 구현한다 JeeInho
    //가입조건을 충족하지 못하면, 가입실패 메시지를 보낸다 (AlreadyEnrolledException 발생시 가입조건을 충족하지 못한것으로 간주)
    @ExceptionHandler(AlreadyEnrolledException.class)
    public String handleAlreadyEnrolledException(AlreadyEnrolledException e) {
        Long discordId = e.getUser().getDiscordId();//유저의 Discord ID를 가져온다
        MessageChannel channel = sogoBot.getPrivateChannelByUserId(discordId);//Discord ID를 통해 개인채널을 가져온다
        //가져온 개인채널에 가입실패 메시지를 보낸다
        chat(messageFactory.generateAuthorizeFailureMessage(FailureReason.ALREADY_ENROLLED), //이미 가입된 유저 인 경우 FailureReason.ALREADY_ENROLLED 을 사용한다
                channel.getIdLong());
        return "discord/enroll/error/already-enrolled";//가입실패(AlreadyEnrolled) 페이지로 이동한다
    }
}
