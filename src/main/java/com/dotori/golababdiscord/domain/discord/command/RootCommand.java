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
        throw new WrongArgumentException(args, "명령어의 인자를 입력해주세요!");
    }

    public boolean execute(String prefix, User user, MessageChannel channel, String args) {
        boolean isCommand = commandTrigger.checkTrigger(prefix);
        if(isCommand) execute(user, channel, args);
        return isCommand;
    }
}
