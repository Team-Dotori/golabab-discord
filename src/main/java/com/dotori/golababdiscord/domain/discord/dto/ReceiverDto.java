package com.dotori.golababdiscord.domain.discord.dto;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.ArrayList;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public class ReceiverDto extends ArrayList<MessageChannel> {
    public ReceiverDto(MessageChannel channel) {
        add(channel);
    }
}
