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

import java.nio.channels.Channel;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Service
@RequiredArgsConstructor
public class VoteConfigurationServiceImpl implements VoteConfigurationService{
    private final BotProperty botProperty;
    private final SogoBot sogoBot;
    private final MessageViews messageViews;
    private final MessageSenderService messageSenderService;

    @Override
    public void changeChannel(long channelId) {
        botProperty.setVoteChannel(channelId);
    }

    @Override
    public void checkChannel(long userId) {
        User user = sogoBot.getUserById(userId);
        ReceiverDto receiver = new ReceiverDto(sogoBot.getVoteChannel());
        MessageDto message = messageViews.generateCheckChannelMessage(user);

        messageSenderService.sendMessage(receiver, message);
    }
}
