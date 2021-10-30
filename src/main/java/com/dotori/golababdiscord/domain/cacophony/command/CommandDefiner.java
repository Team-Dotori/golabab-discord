package com.dotori.golababdiscord.domain.cacophony.command;

import com.dotori.golababdiscord.domain.cacophony.action.AuthorizeAction;
import com.dotori.golababdiscord.domain.cacophony.action.VoteChannelAction;
import com.dotori.golababdiscord.domain.cacophony.action.VoteOpenAction;
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
    private final AuthorizeAction authorizeAction;
    private final VoteOpenAction voteOpenAction;
    private final VoteChannelAction voteChannelAction;

    public CommandDefiner(AuthorizeAction authorizeAction, VoteOpenAction voteOpenAction, VoteChannelAction voteChannelAction, CommandManager commandManager) {
        this.authorizeAction = authorizeAction;
        this.voteOpenAction = voteOpenAction;
        this.voteChannelAction = voteChannelAction;
        RootCommandGenerator.init(commandManager);

        root("라밥이",
                command("인증",
                        action(this::authorize)),
                command("투표",
                        command("열기",
                                command("조식",
                                        action(() -> openVote(MealType.BREAKFAST))
                                ),
                                command("중식",
                                        action(() -> openVote(MealType.LUNCH))
                                ),
                                command("석식",
                                        action(() -> openVote(MealType.DINNER))
                                )
                        ),
                        command("채널",
                                command("확인", action(this::checkVoteChannel)),
                                command("변경", action(this::changeVoteChannel))
                        )
                )
        ).complete();
    }

    private void authorize(Argument argument, UserDto author, ChannelDto channel) {
        authorizeAction.authorize(argument, author, channel);
    }

    private void openVote(MealType type) {
        voteOpenAction.openVote(type);
    }

    private void changeVoteChannel(Argument argument, UserDto author, ChannelDto channel) {
        voteChannelAction.changeVoteChannel(argument, author, channel);
    }

    private void checkVoteChannel(Argument argument, UserDto author, ChannelDto channel) {
        voteChannelAction.checkVoteChannel(argument, author, channel);
    }
}
