package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Service
public class MessageSenderServiceImpl implements MessageSenderService {
    @Override
    public void editMessageToClose(Message origin, EmbedMessageDto edit) {
        MessageEmbed message = edit.toEmbed();
        origin.editMessageEmbeds(message).complete();
    }

    @Override
    public void clearReactions(Message message) {
        message.clearReactions().complete();
    }
}
