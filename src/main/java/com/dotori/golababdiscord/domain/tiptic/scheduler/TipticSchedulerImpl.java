package com.dotori.golababdiscord.domain.tiptic.scheduler;

import com.dotori.golababdiscord.domain.api.service.caller.ApiCaller;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.message.MessageFactory;
import io.github.key_del_jeeinho.cacophony_lib.global.dto.message.EmbedMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

import static io.github.key_del_jeeinho.cacophony_lib.domain.action.ActionEntry.chat;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class TipticSchedulerImpl implements TipticScheduler{
    private final ApiCaller apiCaller;
    private final MessageFactory messageFactory;
    private final SogoBot sogoBot;
    private static final int percentage = 30;

    @Override @Scheduled(cron = "0 0 12 * * *") //매일 12시마다 팁틱 게시
    public void trySendTipticMessage() {
        if((random100()) < percentage) sendTipticMessage();
    }

    private int random100() {
        return new Random().nextInt(100) + 1;
    }

    private void sendTipticMessage() {
        String tiptic = apiCaller.getImproveMessage();

        EmbedMessageDto message = messageFactory.generateTipticMessage(tiptic);

        chat(message, sogoBot.getVoteChannelId());
    }
}
