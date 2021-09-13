package com.dotori.golababdiscord.domain.discord.listeners;

import com.dotori.golababdiscord.domain.discord.command.RootCommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CommandListener extends ListenerAdapter {
    private final RootCommand rootCommand;
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        rootCommand.execute(event.getAuthor(), event.getChannel(), event.getMessage().getContentRaw());
    }
}
