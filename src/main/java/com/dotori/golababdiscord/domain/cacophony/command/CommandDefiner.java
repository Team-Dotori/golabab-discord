package com.dotori.golababdiscord.domain.cacophony.command;

import com.dotori.golababdiscord.domain.cacophony.command.action.AuthorizeAction;
import com.dotori.golababdiscord.domain.cacophony.command.action.VoteChannelAction;
import com.dotori.golababdiscord.domain.cacophony.command.action.VoteOpenAction;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.ChannelDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static io.github.key_del_jeeinho.cacophony_lib.domain.command.CommandEntry.*;

//디스코드 봇에 사용할 모든 Command 를 명시하는 클래스
@Component
@RequiredArgsConstructor
public class CommandDefiner {
    @PostConstruct //컴포넌트 생성시 Command 들을 명시한다
    public void defineCommand() {
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

    private void changeVoteChannel(Argument argument, UserDto author, ChannelDto channel) {

    }

    private void checkVoteChannel(Argument argument, UserDto author, ChannelDto channel) {

    }

    private void openVote(MealType type) {

    }

    private void authorize(Argument argument, UserDto author, ChannelDto channel) {

    }
}
