package com.dotori.golababdiscord.domain.authorize.controller;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import com.dotori.golababdiscord.domain.authorize.service.AuthorizeService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.enroll.exception.AlreadyEnrolledException;
import com.dotori.golababdiscord.domain.enroll.service.EnrollService;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorizeControllerWithMessaging extends AuthorizeController{
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;

    public AuthorizeControllerWithMessaging(AuthorizeService authorizeService,
                                            EnrollService enrollService,
                                            MessageSenderService messageSenderService,
                                            MessageViews messageViews,
                                            SogoBot sogoBot) {
        super(authorizeService, enrollService, sogoBot);
        this.messageSenderService = messageSenderService;
        this.messageViews = messageViews;
    }

    @Override
    protected void sendAuthorizedMessage(Long discordId) {
        MessageChannel channel = sogoBot.getPrivateChannelByUserId(discordId);
        messageSenderService.sendMessage(new ReceiverDto(channel),
                messageViews.generateAuthorizedMessage());
    }

    @Override
    public String handleAlreadyEnrolledException(AlreadyEnrolledException e) {
        Long discordId = e.getUser().getDiscordId();
        MessageChannel channel = super.sogoBot.getPrivateChannelByUserId(discordId);
        messageSenderService.sendMessage(new ReceiverDto(channel),
                messageViews.generateAuthorizeFailureMessage(FailureReason.ALREADY_ENROLLED));
        return super.handleAlreadyEnrolledException(e);
    }
}
