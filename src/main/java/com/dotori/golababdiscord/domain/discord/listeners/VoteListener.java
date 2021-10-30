package com.dotori.golababdiscord.domain.discord.listeners;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.listeners.handler.VoteHandler;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@Slf4j
public class VoteListener extends ListenerAdapter {
    private final VoteHandler voteHandler;

    public VoteListener(VoteHandler voteHandler, SogoBot sogoBot) {
        this.voteHandler = voteHandler;
        sogoBot.addEventListener(this);
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if(event.getUser().isBot()) return;
        voteHandler.handleEvent(event);
    }
}
