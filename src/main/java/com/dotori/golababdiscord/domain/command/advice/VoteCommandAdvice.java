package com.dotori.golababdiscord.domain.command.advice;

import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.message.MessageFactory;
import com.dotori.golababdiscord.domain.user.service.UserService;
import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.ChannelDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.UserDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;
import static io.github.key_del_jeeinho.cacophony_lib.domain.converter.ConverterEntry.userToDM;

@Component
@RequiredArgsConstructor
public class VoteCommandAdvice {
    private final MessageFactory messageFactory;
    private final UserService userService;

    public void handlePermissionDeniedException(Argument argument, UserDto user, ChannelDto channel, PermissionDeniedException e) {
        EmbedMessageDto message = messageFactory.generatePermissionDeniedMessage(//권한 부족으로 인한 오류메세지를 생성한다
                userService.getUserDto(user.getId()).getPermission(), e.getFeature());//사용자의 권한과 사용자가 사용한 기능을 받아옴
        chat(message, userToDM(user.getId()).getId());
    }
}
