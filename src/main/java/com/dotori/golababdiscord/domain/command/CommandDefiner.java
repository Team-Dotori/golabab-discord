package com.dotori.golababdiscord.domain.command;

import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.command.action.AuthorizeAction;
import com.dotori.golababdiscord.domain.command.action.VoteChannelAction;
import com.dotori.golababdiscord.domain.command.action.VoteOpenAction;
import com.dotori.golababdiscord.domain.command.advice.AuthorizeCommandAdvice;
import com.dotori.golababdiscord.domain.command.advice.VoteCommandAdvice;
import com.dotori.golababdiscord.domain.command.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.RootCommandGenerator;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.manager.CommandManager;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.ChannelDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import static io.github.key_del_jeeinho.cacophony_lib.domain.command.CommandEntry.*;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//디스코드 봇에 사용할 모든 Command 를 명시하는 클래스
@Component
@DependsOn("commandManager")
public class CommandDefiner {
    private final BotProperty property;

    private final AuthorizeAction authorizeAction;
    private final VoteOpenAction voteOpenAction;
    private final VoteChannelAction voteChannelAction;

    private final AuthorizeCommandAdvice authorizeAdvice;
    private final VoteCommandAdvice voteAdvice;

    public CommandDefiner(BotProperty property,
                          AuthorizeAction authorizeAction,
                          VoteOpenAction voteOpenAction,
                          VoteChannelAction voteChannelAction,
                          CommandManager commandManager,
                          AuthorizeCommandAdvice authorizeAdvice,
                          VoteCommandAdvice voteAdvice) {
        this.authorizeAction = authorizeAction;
        this.voteOpenAction = voteOpenAction;
        this.voteChannelAction = voteChannelAction;
        this.property = property;
        this.authorizeAdvice = authorizeAdvice;
        this.voteAdvice = voteAdvice;
        RootCommandGenerator.init(commandManager);

        root(property.getCommandPrefix(),
                //라밥아 인증
                command("인증",
                        action(this::authorize)
                ).whenThrow(WrongArgumentException.class, this::handleWrongArgumentException)
                        .whenThrow(DepartmentNotFoundException.class, this::handleDepartmentNotFoundException),
                //라밥아 투표
                command("투표",
                        //라밥아 투표 열기
                        command("열기",
                                //라밥아 투표 열기 조식
                                command("조식", action((a, user) -> openVote(MealType.BREAKFAST, user)))
                                        .whenThrow(PermissionDeniedException.class, this::handlePermissionDeniedException),
                                //라밥아 투표 열기 중식
                                command("중식", action((a, user) -> openVote(MealType.LUNCH, user)))
                                        .whenThrow(PermissionDeniedException.class, this::handlePermissionDeniedException),
                                //라밥아 투표 열기 석식
                                command("석식", action((a, user) -> openVote(MealType.DINNER, user)))
                                        .whenThrow(PermissionDeniedException.class, this::handlePermissionDeniedException)
                        ),
                        //라밥아 투표 채널
                        command("채널",
                                //라밥아 투표 채널 확인
                                command("확인", action(this::checkVoteChannel)),
                                //라밥아 투표 채널 변경
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
