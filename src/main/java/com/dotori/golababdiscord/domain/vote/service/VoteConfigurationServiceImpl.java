package com.dotori.golababdiscord.domain.vote.service;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteConfigurationServiceImpl implements VoteConfigurationService{
    private final BotProperty botProperty;
    private final SogoBot sogoBot;
    private final MessageViews messageViews;
    private final MessageSenderService messageSenderService;

    @Override
    public void changeChannel(MessageChannel channel) {
        botProperty.setVoteChannel(channel.getIdLong());
    }

    @Override
    public void checkChannel(User user) {
        ReceiverDto receiver = new ReceiverDto(sogoBot.getVoteChannel());
        MessageDto message = messageViews.generateCheckChannelMessage(user);

        messageSenderService.sendMessage(receiver, message);
    }
}
