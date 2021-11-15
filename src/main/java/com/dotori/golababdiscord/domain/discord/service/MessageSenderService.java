package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import net.dv8tion.jda.api.entities.Message;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface MessageSenderService {
    void editMessageToClose(Message origin, EmbedMessageDto edit);
    void clearReactions(Message message);
}
