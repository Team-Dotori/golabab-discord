package com.dotori.golababdiscord.domain.command.node;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public abstract class LeafCommand extends Command{
    public LeafCommand(String prefix) {
        super(prefix);
    }

    @Override
    public boolean execute(User user, MessageChannel channel, String args) {
        run(user, channel, args);
        return true;
    }
}
