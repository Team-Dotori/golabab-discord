package com.dotori.golababdiscord.domain.tiptic.scheduler;

import com.dotori.golababdiscord.domain.api.service.TipticApiService;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import org.springframework.stereotype.Component;

@Component
public class DiscordTipticSchedulerImpl extends TipticSchedulerImpl{
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
    private final SogoBot sogoBot;

    public DiscordTipticSchedulerImpl(TipticApiService tipticApiService, MessageSenderService messageSenderService, MessageViews messageViews, SogoBot sogoBot) {
        super(tipticApiService);
        this.messageSenderService = messageSenderService;
        this.messageViews = messageViews;
        this.sogoBot = sogoBot;
    }

    @Override
    protected void sendTiptic(String tiptic) {
        ReceiverDto receiver = new ReceiverDto(sogoBot.getVoteChannel());
        MessageDto message = messageViews.generateTipticMessage(tiptic);

        messageSenderService.sendMessage(receiver, message);
    }
}
