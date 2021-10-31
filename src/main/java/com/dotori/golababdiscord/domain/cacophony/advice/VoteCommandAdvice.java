package com.dotori.golababdiscord.domain.cacophony.advice;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.exception.PermissionDeniedException;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
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
    private final MessageViews messageViews;
    private final UserService userService;

    public void handlePermissionDeniedException(Argument argument, UserDto user, ChannelDto channel, PermissionDeniedException e) {
        MessageDto legacyMessage = messageViews.generatePermissionDeniedMessage(//권한 부족으로 인한 오류메세지를 생성한다
                userService.getUserDto(user.getId()).getPermission(), e.getFeature());//사용자의 권한과 사용자가 사용한 기능을 받아옴

        //TODO 2021.10.30 메세지 로직 개편 후 삭제 JeeInho
        EmbedMessageDto message = MessageViews.getEmbedMessageByLegacyMessageDto(legacyMessage);//불러온 레거시 메세지를 현재 스펙의 메세지로 치환한다(Message 로직 리펙터링 후 제거예정)

        chat(message, userToDM(user.getId()).getId());
    }
}
