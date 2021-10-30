package com.dotori.golababdiscord.domain.command.node;

import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.command.exception.UnknownCommandException;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.command.function.AuthorizeCommand;
import com.dotori.golababdiscord.domain.command.function.VoteChannelCommand;
import com.dotori.golababdiscord.domain.command.function.VoteCommand;
import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import com.dotori.golababdiscord.domain.vote.service.VoteConfigurationService;
import com.dotori.golababdiscord.infra.service.MailService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//커멘드중 최상위 커멘드에 대한 클래스
@Component
@Slf4j
public class RootCommand extends Command{
    private final BotProperty botProperty;//봇의 속성
    //기본 생성자
    public RootCommand(BotProperty botProperty,
                       MailService mailService,
                       MessageSenderService messageSenderService,
                       AuthorizeService authorizeService,
                       MessageViews messageViews,
                       SpringTemplateEngine templateEngine,
                       VoteScheduler voteScheduler,
                       VoteConfigurationService voteConfigurationService,
                       UserService userService,
                       SogoBot sogoBot) {
        super(botProperty.getCommandPrefix());
        initChildren(mailService, messageSenderService, authorizeService, messageViews, templateEngine, voteScheduler, voteConfigurationService, userService, sogoBot);
        initDepth(0);
        this.botProperty = botProperty;
    }

    //자식들을 초기화하는 메서드
    private void initChildren(MailService mailService,
                              MessageSenderService messageSenderService,
                              AuthorizeService authorizeService,
                              MessageViews messageViews,
                              SpringTemplateEngine templateEngine,
                              VoteScheduler voteScheduler,
                              VoteConfigurationService voteConfigurationService,
                              UserService userService,
                              SogoBot sogoBot) {
        AuthorizeCommand authorizeCommand =//인증 커맨드
                new AuthorizeCommand("인증", mailService, messageSenderService, authorizeService, messageViews, templateEngine);
        VoteCommand voteCommand =//투표 커맨드
                new VoteCommand("투표", voteScheduler);
        VoteChannelCommand voteChannelCommand =//투표 채널 관리 커맨드
                new VoteChannelCommand("채널", voteConfigurationService, userService, messageViews, messageSenderService, sogoBot);

        this.addChild(authorizeCommand);//인증 커맨드를 하위커맨드로 추가
        this.addChild(voteCommand);//투표 커맨드를 하위커맨드로 추가
        voteCommand.addChild(voteChannelCommand);//투표 채널 관리 커맨드를 투표 커맨드의 하위커맨드로 추가
    }

    @Override //최상위 커맨드가 실행될경우 실행되는 메서드
    protected void run(User user, MessageChannel channel, String args) {
        throw new WrongArgumentException(args, "존재하지 않는 명령어입니다!");//오롯이 최상위 커맨드자체로 실행되는 커맨드작업은 없으므로 예외를 발생시킨다
    }

    @Override//자식들을 실행하는 메서드
    public boolean execute(User user, MessageChannel channel, String args) {
        String prefix = getRootInputPrefix(args);//자식들을 실행하기 위해 입력된 명령어의 접두어를 가져온다
        String childArgs = encodeRootArgsByInput(args);//자식들을 실행하기 위해 입력된 명령어의 접두어를 제외한 나머지 명령어를 가져온다
        if(commandTrigger.checkTrigger(prefix)) {//해당 메서드의 접두어와 전달된 접두어가 같은지 확인한다
            super.execute(user, channel, childArgs);//자식들을 실행한다 (단, 자식과 일치하는 접두어를 찾을 수 없을경우, 해당 커맨드를 실행하여 예외를 발생시킨다)
            return true;//자식들을 실행한 경우 true를 반환한다
        }
        return false;//자식들을 실행하지 않은 경우 false를 반환한다
    }

    //RootCommand 를 실행시키는 메서드
    public void executeRoot(User user, MessageChannel channel, String args) {
        if(!execute(user, channel, args)) throw new UnknownCommandException(args);//만약 정상적으로 실행되지 않았을경우, UnknownCommandException 을 발생시킨다
    }

    //입력을 통해 자식커맨드의 인자를 구한다
    private String encodeRootArgsByInput(String args) {
        if(!args.contains(" ")) return "";//만약 자식노드의 커맨드로 전달할 인자가 존재하지 않을경우 빈 문자열을 반환한다
        return args.substring(args.indexOf(" ") + 1);//해당 커맨드를 지칭하는 접두어를 제거한 문자열을 반환한다
    }

    //입력된 문자열로부터 최상위 커맨드의 접두어로 추정되는 문자열을 가져온다
    private String getRootInputPrefix(String args) {
        return args.split(" ")[0];
    }
}
