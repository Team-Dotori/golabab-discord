package com.dotori.golababdiscord.domain.discord.listeners;

import com.dotori.golababdiscord.domain.discord.listeners.handler.VoteHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Slf4j
public class VoteListener extends ListenerAdapter {
    private final VoteHandler voteHandler;

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if(event.getUser().isBot()) return;
        voteHandler.handleEvent(event);
    }
}
