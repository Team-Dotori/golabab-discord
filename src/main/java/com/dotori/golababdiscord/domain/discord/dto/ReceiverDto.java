package com.dotori.golababdiscord.domain.discord.dto;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.ArrayList;

public class ReceiverDto extends ArrayList<MessageChannel> {
    public ReceiverDto(MessageChannel channel) {
        add(channel);
    }
}
