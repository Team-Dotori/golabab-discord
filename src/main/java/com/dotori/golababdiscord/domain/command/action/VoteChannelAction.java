package com.dotori.golababdiscord.domain.command.action;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.message.MessageFactory;
import com.dotori.golababdiscord.domain.permission.enum_type.Feature;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.service.UserService;
import com.dotori.golababdiscord.domain.vote.service.VoteConfigurationService;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.ChannelDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;
import static io.github.key_del_jeeinho.cacophony_lib.domain.converter.ConverterEntry.userById;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class VoteChannelAction {
    private final VoteConfigurationService voteConfigurationService;//투표 환경설정 서비스
    private final UserService userService;
    private final MessageFactory messageFactory;
    private final SogoBot sogoBot;

    public void changeVoteChannel(Argument argument, UserDto author, ChannelDto channel) {
        if(!checkPermission(author.getId())) throw new PermissionDeniedException(Feature.GOLABAB_MANAGE);

        voteConfigurationService.changeChannel(channel.getId());//채널 변경
        sendChannelChangedMessage(channel.getId(), author.getId());//채널 변경 완료 메시지 전송
    }
    public void checkVoteChannel(Argument argument, UserDto author, ChannelDto channel) {
        if(!checkPermission(author.getId())) throw new PermissionDeniedException(Feature.GOLABAB_MANAGE);

        voteConfigurationService.checkChannel(author.getId());//채널 확인
        if(channel.getId() != sogoBot.getVoteChannelId())//현재 채널이 투표채널이 아니면
            sendCheckChannelMessage(channel.getId());//채널 확인 완료 메시지 전송 (투표채널에 확인메세지를 전송하였습니다!)
    }


    //채널 변경 완료 메시지를 전송하는 메소드
    private void sendChannelChangedMessage(long channelId, long userId) {
        EmbedMessageDto message = messageFactory.generateChannelChangedMessage(userById(userId));//채널 변경 확인 메세지를 불러온다
        chat(message, channelId);//채팅 전송
    }

    //채널 확인 완료 메세지를 전송하는 메서드
    private void sendCheckChannelMessage(long channelId) {
        EmbedMessageDto message = messageFactory.generateCheckChannelAlarmMessage();//채널 확인 완료 메세지를 전송한다
        chat(message, channelId);//메세지를 전송한다
    }

    //관리 권한이 있는지 확인하는 메소드
    private boolean checkPermission(long userId) {
        SogoPermission permission = userService.getUserDto(userId).getPermission();//유저의 권한을 가져옴
        return permission.isHaveFeature(Feature.GOLABAB_MANAGE);//관리 권한 소유 여부를 반환
    }
}
