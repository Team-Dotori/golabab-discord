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
        String args = event.getMessage().getContentRaw();
        String prefix = getRootInputPrefix(args);
        String childArgs = encodeRootArgsByInput(args);
        rootCommand.execute(prefix, event.getAuthor(), event.getChannel(), childArgs);
    }

    private String encodeRootArgsByInput(String args) {
        if(!args.contains(" ")) return "";
        return args.substring(args.indexOf(" ") + 1);
    }

    private String getRootInputPrefix(String args) {
        return args.split(" ")[0];
    }
}
