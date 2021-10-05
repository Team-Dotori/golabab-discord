package com.dotori.golababdiscord.domain.command.node;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class LeafCommand extends Command{
    public LeafCommand(String prefix) {
        super(prefix);
    }

    @Override
    public void execute(User user, MessageChannel channel, String args) {
        run(user, channel, args);
    }
}
