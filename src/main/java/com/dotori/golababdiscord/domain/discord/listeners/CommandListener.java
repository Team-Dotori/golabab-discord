package com.dotori.golababdiscord.domain.discord.listeners;

import com.dotori.golababdiscord.domain.discord.listeners.handler.CommandHandler;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CommandListener extends ListenerAdapter {
    private final CommandHandler commandHandler;
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        commandHandler.handleEvent(event);
    }
}
