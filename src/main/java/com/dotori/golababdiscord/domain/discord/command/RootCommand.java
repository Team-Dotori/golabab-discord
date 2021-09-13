package com.dotori.golababdiscord.domain.discord.command;

import com.dotori.golababdiscord.domain.discord.exception.WrongArgumentException;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class RootCommand extends Command{
    public RootCommand(String prefix) {
        super(prefix);
    }

    @Override
    protected void run(User user, MessageChannel channel, String args) {
        throw new WrongArgumentException(args);
    }

    @Override
    public void execute(User user, MessageChannel channel, String args) {
        String prefix = getRootInputPrefix(args);
        String childArgs = encodeRootArgsByInput(args);
        if(commandTrigger.checkTrigger(prefix)) {
            super.execute(user, channel, childArgs);
        }
    }

    private String encodeRootArgsByInput(String args) {
        if(!args.contains(" ")) return "";
        return args.substring(args.indexOf(" ") + 1);
    }

    private String getRootInputPrefix(String args) {
        return args.split(" ")[0];
    }
}
