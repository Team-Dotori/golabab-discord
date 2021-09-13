package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;

public interface MessageSenderService {
    void sendMessage(ReceiverDto receiver, MessageDto message);
}
