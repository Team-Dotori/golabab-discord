package com.dotori.golababdiscord.domain.discord.dto;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceiverDto {
    private final List<MessageChannel> receivers;

    public ReceiverDto() {
        this.receivers = new ArrayList<>();
    }

    public void addReceiver(MessageChannel channel) {
        receivers.add(channel);
    }

    public void addReceivers(MessageChannel... channels) {
        receivers.addAll(Arrays.asList(channels));
    }
}
