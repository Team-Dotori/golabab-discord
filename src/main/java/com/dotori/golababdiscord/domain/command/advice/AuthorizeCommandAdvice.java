package com.dotori.golababdiscord.domain.command.advice;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.command.exception.WrongArgumentException;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.enum_type.WrongCommandUsageType;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.message.MessageFactory;
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
    private final MessageFactory messageFactory;

    public void handleWrongArgumentException(Argument a, UserDto u, ChannelDto channel, WrongArgumentException e) {
        String argStr = getArgStrToArgument(e.getArgument());

        EmbedMessageDto message = messageFactory.generateWrongCommandUsageMessage(WrongCommandUsageType.WRONG_ARGUMENT, argStr, e.getUsage());
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

    public void handleDepartmentNotFoundException(Argument a, UserDto b, ChannelDto channel, DepartmentNotFoundException d) {
        EmbedMessageDto message = messageFactory.generateAuthorizeFailureMessage(FailureReason.DOMAIN_IS_NOT_SCHOOL_DOMAIN);
        chat(message, channel.getId());
    }
}
