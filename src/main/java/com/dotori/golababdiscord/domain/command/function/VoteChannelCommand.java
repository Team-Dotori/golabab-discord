package com.dotori.golababdiscord.domain.command.function;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.command.node.LeafCommand;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.service.VoteConfigurationService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//투표채널을 관리하는 커멘드 클래스
public class VoteChannelCommand extends LeafCommand {
    private final VoteConfigurationService voteConfigurationService;//투표 환경설정 서비스
    private final MessageSenderService messageSenderService;//메시지 전송 서비스
    private final UserService userService;//유저 서비스
    private final MessageViews messageViews;//메시지 뷰
    private final SogoBot sogoBot;//디스코드 봇

    //기본 생성자
    public VoteChannelCommand(String prefix, VoteConfigurationService voteConfigurationService, UserService userService, MessageViews messageViews, MessageSenderService messageSenderService, SogoBot sogoBot) {
        super(prefix);
        this.voteConfigurationService = voteConfigurationService;
        this.userService = userService;
        this.messageViews = messageViews;
        this.messageSenderService = messageSenderService;
        this.sogoBot = sogoBot;
    }

    @Override//커멘드 실행시 실행되는 메소드
    protected void run(User user, MessageChannel channel, String args) {
        if(!checkPermission(user)) throw new PermissionDeniedException(Feature.GOLABAB_MANAGE);//관리 권한이 없으면 권한 에러 발생
        if(args.equals("변경")) {//채널 변경을 요청한 경우
            voteConfigurationService.changeChannel(channel.getIdLong());//채널 변경
            sendChannelChangedMessage(channel, user);//채널 변경 완료 메시지 전송
        } else if(args.equals("확인")) {//채널 확인을 요청한 경우
            voteConfigurationService.checkChannel(user.getIdLong());//채널 확인
            if(channel.getIdLong() != sogoBot.getVoteChannel().getIdLong())//현재 채널이 투표채널이 아니면
                sendCheckChannelMessage(channel);//채널 확인 완료 메시지 전송 (투표채널에 확인메세지를 전송하였습니다!) 
        }
    }

    //관리 권한이 있는지 확인하는 메소드
    private boolean checkPermission(User user) {
        SogoPermission permission = userService.getUserDto(user.getIdLong()).getPermission();//유저의 권한을 가져옴
        return permission.isHaveFeature(Feature.GOLABAB_MANAGE);//관리 권한 소유 여부를 반환
    }

    //채널 변경 완료 메시지를 전송하는 메소드
    private void sendChannelChangedMessage(MessageChannel channel, User user) {
        ReceiverDto receiver = new ReceiverDto(channel);//전송할 채널을 설정한다
        MessageDto message = messageViews.generateChannelChangedMessage(user);//채널 변경 확인 메세지를 불러온다

        messageSenderService.sendMessage(receiver, message);//메세지를 전송한다
    }

    //채널 확인 완료 메세지를 전송하는 메서드
    private void sendCheckChannelMessage(MessageChannel channel) {
        ReceiverDto receiver = new ReceiverDto(channel);//전송할 채널을 설정한다
        MessageDto message = messageViews.generateCheckChannelAlarmMessage();//채널 확인 완료 메세지를 전송한다

        messageSenderService.sendMessage(receiver, message);//메세지를 전송한다
    }
}
