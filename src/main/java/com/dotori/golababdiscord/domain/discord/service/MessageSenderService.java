package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import net.dv8tion.jda.api.entities.Message;

public interface MessageSenderService {
    Long sendMessage(ReceiverDto receiver, MessageDto message);
    void editMessageToClose(Message origin, MessageDto edit);
    void clearReactions(Message message);
}
