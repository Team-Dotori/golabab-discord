package com.dotori.golababdiscord.domain.discord.listeners.handler;

import com.dotori.golababdiscord.domain.command.node.RootCommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class CommandHandler implements Handler<MessageReceivedEvent>{
    private final RootCommand rootCommand;

    @Override
    public void handleEvent(MessageReceivedEvent event) {
        rootCommand.executeRoot(event.getAuthor(), event.getChannel(), event.getMessage().getContentRaw());
    }
}
