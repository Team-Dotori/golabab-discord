package com.dotori.golababdiscord.domain.command.advice;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.command.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.ChannelDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;

@Component
@RequiredArgsConstructor
public class AuthorizeCommandAdvice {
    private final MessageViews messageViews;
    public void handleWrongArgumentException(Argument a, UserDto u, ChannelDto channel, WrongArgumentException e) {
        String argStr = getArgStrToArgument(e.getArgument());

        MessageDto legacyMessage = messageViews.generateWrongCommandUsageMessage(WrongCommandUsageType.WRONG_ARGUMENT, argStr, e.getUsage());

        //TODO 2021.10.31 메세지 로직 개편 후 삭제 JeeInho
        EmbedMessageDto message = MessageViews.getEmbedMessageByLegacyMessageDto(legacyMessage);//불러온 레거시 메세지를 현재 스펙의 메세지로 치환한다(Message 로직 리펙터링 후 제거예정)
        chat(message, channel.getId());
    }

    private String getArgStrToArgument(Argument argument) {
        StringBuilder sb = new StringBuilder();
        while(argument != null) {
            sb.append(argument.getArgument()).append(" ");
            argument = argument.getNext();
        }
        return sb.toString();
    }

    public void handleDepartmentNotFoundException(Argument argument, UserDto user, ChannelDto channel, DepartmentNotFoundException e) {
        MessageDto legacyMessage = messageViews.generateAuthorizeFailureMessage(FailureReason.DOMAIN_IS_NOT_SCHOOL_DOMAIN);

        //TODO 2021.10.31 메세지 로직 개편 후 삭제 JeeInho
        EmbedMessageDto message = MessageViews.getEmbedMessageByLegacyMessageDto(legacyMessage);//불러온 레거시 메세지를 현재 스펙의 메세지로 치환한다(Message 로직 리펙터링 후 제거예정)
        chat(message, channel.getId());
    }
}
