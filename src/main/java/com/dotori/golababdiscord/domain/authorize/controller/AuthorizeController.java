package com.dotori.golababdiscord.domain.authorize.controller;

import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.enroll.exception.AlreadyEnrolledException;
import com.dotori.golababdiscord.domain.enroll.service.EnrollService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {
    private final AuthorizeService authorizeService;
    private final EnrollService enrollService;
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
    private final SogoBot sogoBot;

    @RequestMapping("/authorize")
    public String authorize(@RequestParam String token) {
        ValidatedUserDto validatedUser = authorizeService.validateAuthorizeLink(token);
        enrollService.checkEnrollCondition(validatedUser.toUserDto());
        sendAuthorizedMessage(validatedUser.getDiscordId());

        enrollService.enroll(validatedUser.toUserDto());

        return "discord/authorize/authorized";
    }

    private void sendAuthorizedMessage(Long discordId) {
        MessageChannel channel = sogoBot.getPrivateChannelByUserId(discordId);
        messageSenderService.sendMessage(new ReceiverDto(channel),
                messageViews.generateAuthorizedMessage());
    }

    @ExceptionHandler(AlreadyEnrolledException.class)
    public String handleAlreadyEnrolledException(AlreadyEnrolledException e) {
        Long discordId = e.getUser().getDiscordId();
        MessageChannel channel = sogoBot.getPrivateChannelByUserId(discordId);
        messageSenderService.sendMessage(new ReceiverDto(channel),
                messageViews.generateAuthorizeFailureMessage(FailureReason.ALREADY_ENROLLED));
        return "discord/enroll/error/already-enrolled";
    }
}
