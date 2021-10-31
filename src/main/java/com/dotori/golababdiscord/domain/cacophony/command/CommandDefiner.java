package com.dotori.golababdiscord.domain.cacophony.command;

import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.cacophony.action.AuthorizeAction;
import com.dotori.golababdiscord.domain.cacophony.action.VoteChannelAction;
import com.dotori.golababdiscord.domain.cacophony.action.VoteOpenAction;
import com.dotori.golababdiscord.domain.cacophony.advice.AuthorizeCommandAdvice;
import com.dotori.golababdiscord.domain.cacophony.advice.VoteCommandAdvice;
import com.dotori.golababdiscord.domain.cacophony.advice.VoteChannelCommandAdvice;
import com.dotori.golababdiscord.domain.cacophony.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.RootCommandGenerator;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.manager.CommandManager;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.ChannelDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;
import static io.github.key_del_jeeinho.cacophony_lib.domain.command.CommandEntry.*;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//디스코드 봇에 사용할 모든 Command 를 명시하는 클래스
@Component
@DependsOn("commandManager")
public class CommandDefiner {
    private final AuthorizeAction authorizeAction;
    private final VoteOpenAction voteOpenAction;
    private final VoteChannelAction voteChannelAction;

    private final AuthorizeCommandAdvice authorizeAdvice;
    private final VoteCommandAdvice voteAdvice;
    private final VoteChannelCommandAdvice voteChannelAdvice;

    public CommandDefiner(AuthorizeAction authorizeAction, VoteOpenAction voteOpenAction, VoteChannelAction voteChannelAction, CommandManager commandManager, AuthorizeCommandAdvice authorizeAdvice, VoteCommandAdvice voteAdvice, VoteChannelCommandAdvice voteChannelAdvice) {
        this.authorizeAction = authorizeAction;
        this.voteOpenAction = voteOpenAction;
        this.voteChannelAction = voteChannelAction;
        this.authorizeAdvice = authorizeAdvice;
        this.voteAdvice = voteAdvice;
        this.voteChannelAdvice = voteChannelAdvice;
        RootCommandGenerator.init(commandManager);

        root("라밥이",
                //라밥이 인증
                command("인증",
                        action(this::authorize)
                ).whenThrow(WrongArgumentException.class, this::handleWrongArgumentException)
                        .whenThrow(DepartmentNotFoundException.class, this::handleDepartmentNotFoundException),
                //라밥이 투표
                command("투표",
                        //라밥이 투표 열기
                        command("열기",
                                //라밥이 투표 열기 조식
                                command("조식", action((a, user) -> openVote(MealType.BREAKFAST, user))),
                                //라밥이 투표 열기 중식
                                command("중식", action((a, user) -> openVote(MealType.LUNCH, user))),
                                //라밥이 투표 열기 석식
                                command("석식", action((a, user) -> openVote(MealType.DINNER, user)))
                        ),
                        //라밥이 투표 채널
                        command("채널",
                                //라밥이 투표 채널 확인
                                command("확인", action(this::checkVoteChannel)),
                                //라밥이 투표 채널 변경
                                command("변경", action(this::changeVoteChannel))
                        )
                ).whenThrow(PermissionDeniedException.class, this::handlePermissionDeniedException)
        ).complete();
    }

    private void handlePermissionDeniedException(Argument argument, UserDto user, ChannelDto channel, PermissionDeniedException e) {
        voteAdvice.handlePermissionDeniedException(argument, user, channel, e);
    }

    private void handleDepartmentNotFoundException(Argument argument, UserDto userDto, ChannelDto channelDto, DepartmentNotFoundException e) {
        authorizeAdvice.handleDepartmentNotFoundException(argument, userDto, channelDto, e);
    }

    private void handleWrongArgumentException(Argument argument, UserDto userDto, ChannelDto channelDto, WrongArgumentException e) {
        authorizeAdvice.handleWrongArgumentException(argument, userDto, channelDto, e);
    }

    private void authorize(Argument argument, UserDto author, ChannelDto channel) {
        authorizeAction.authorize(argument, author, channel);
    }

    private void openVote(MealType type, UserDto user) {
        voteOpenAction.openVote(type, user);
    }

    private void changeVoteChannel(Argument argument, UserDto author, ChannelDto channel) {
        voteChannelAction.changeVoteChannel(argument, author, channel);
    }

    private void checkVoteChannel(Argument argument, UserDto author, ChannelDto channel) {
        voteChannelAction.checkVoteChannel(argument, author, channel);
    }
}
