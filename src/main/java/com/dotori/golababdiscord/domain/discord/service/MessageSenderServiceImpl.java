package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
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
    public Long sendMessage(ReceiverDto receiver, MessageDto message) {
        MessageEmbed embed = getEmbedMessageByMessage(message);

        AtomicLong messageId = new AtomicLong();
        receiver.forEach(channel -> {
            Message sentMessage = channel.sendMessageEmbeds(embed).complete();
            message.getEmojis().forEach(emoji -> sentMessage.addReaction(emoji).complete());
            messageId.set(sentMessage.getIdLong());
        });

        return messageId.get();
    }

    @Override
    public void editMessageToClose(Message origin, MessageDto edit) {
        MessageEmbed message = getEmbedMessageByMessage(edit);
        origin.editMessageEmbeds(message).complete();
    }

    @Override
    public void clearReactions(Message message) {
        message.clearReactions().complete();
    }

    private MessageEmbed getEmbedMessageByMessage(MessageDto message) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle(message.getTitle().getTitle(), message.getTitle().getUrl())
                .setDescription(message.getDescription())
                .setColor(message.getColor())
                .setAuthor(message.getAuthor().getName(), message.getAuthor().getUrl());

        message.getSections().forEach(
                section -> builder.addField(section.getTitle(), section.getText(), section.getInline()));

        return builder.build();
    }
}
