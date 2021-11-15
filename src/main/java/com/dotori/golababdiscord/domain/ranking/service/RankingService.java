package com.dotori.golababdiscord.domain.ranking.service;

import com.dotori.golababdiscord.domain.api.service.caller.ApiCaller;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.message.MessageFactory;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Service
public class RankingService {
    private final ApiCaller apiCaller;
    private final MessageFactory messageFactory;
    private final SogoBot sogoBot;

    public void sendResult2Weeks() {
        RequestRankingDto ranking = apiCaller.getRanking(0, 19);
        EmbedMessageDto message = messageFactory.generateRankingMessage(ranking);
        chat(message, sogoBot.getVoteChannelId());
    }
}
