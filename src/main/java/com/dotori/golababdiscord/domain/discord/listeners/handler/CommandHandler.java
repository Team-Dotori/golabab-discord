package com.dotori.golababdiscord.domain.discord.listeners.handler;

import com.dotori.golababdiscord.domain.discord.command.node.RootCommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@RequiredArgsConstructor
public class CommandHandler implements Handler<MessageReceivedEvent>{
    private final RootCommand rootCommand;

    @Override
    public void handleEvent(MessageReceivedEvent event) {
        rootCommand.execute(event.getAuthor(), event.getChannel(), event.getMessage().getContentRaw());
    }
}
