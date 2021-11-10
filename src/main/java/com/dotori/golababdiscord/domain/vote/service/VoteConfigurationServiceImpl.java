package com.dotori.golababdiscord.domain.vote.service;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import com.dotori.golababdiscord.domain.message.MessageFactory;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Service
@RequiredArgsConstructor
public class VoteConfigurationServiceImpl implements VoteConfigurationService{
    private final BotProperty botProperty;
    private final SogoBot sogoBot;
    private final MessageFactory messageFactory;

    @Override
    public void changeChannel(long channelId) {
        botProperty.setVoteChannel(channelId);
    }

    @Override
    public void checkChannel(long userId) {
        User user = sogoBot.getUserById(userId);

        EmbedMessageDto message = messageFactory.generateCheckChannelMessage(user);

        chat(message, sogoBot.getVoteChannel().getIdLong());
    }
}
