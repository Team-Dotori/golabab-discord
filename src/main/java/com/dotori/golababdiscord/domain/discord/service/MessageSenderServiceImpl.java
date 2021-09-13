package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderServiceImpl implements MessageSenderService {
    @Override
    public void sendMessage(ReceiverDto receiver, MessageDto message) {
        MessageEmbed embed = getEmbedMessageByMessage(message);
        receiver.forEach(channel -> channel.sendMessageEmbeds(embed).complete());
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
