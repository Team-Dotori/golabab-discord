package com.dotori.golababdiscord.domain.discord;

import com.dotori.golababdiscord.domain.discord.exception.BotBuildingFailureException;
import com.dotori.golababdiscord.domain.discord.exception.UserNotFoundException;
import com.dotori.golababdiscord.domain.discord.exception.VoteChannelNotFoundException;
import com.dotori.golababdiscord.domain.discord.property.BotProperty;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.stereotype.Component;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
public class SogoBot {
    private final JDA jda;
    private final BotProperty botProperty;

    public SogoBot(BotProperty botProperty)  {
        this.botProperty = botProperty;
        try {
            jda = JDABuilder.createDefault(botProperty.getToken()).build();
        } catch (Exception e) {
            throw new BotBuildingFailureException(e);
        }
    }

    public void addEventListener(EventListener listener) {
        jda.addEventListener(listener);
    }
    public MessageChannel getPrivateChannelByUserId(Long userId) {
        return getUserById(userId).openPrivateChannel().complete();
    }

    public User getUserById(Long userId) {
        User user;

        if((user = jda.retrieveUserById(userId).complete()) == null) throw new UserNotFoundException(userId);
        return user;
    }

    public TextChannel getVoteChannel() {
        TextChannel channel;
        if((channel = jda.getTextChannelById(botProperty.getVoteChannel())) == null) throw new VoteChannelNotFoundException(botProperty.getVoteChannel());
        return channel;
    }

    public Message getMessageById(Long voteMessageId) {
        return getVoteChannel().retrieveMessageById(voteMessageId).complete();
    }

    public Guild getOfficialGuild() {
        return getVoteChannel().getGuild();
    }
}
