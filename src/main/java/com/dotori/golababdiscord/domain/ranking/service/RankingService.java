package com.dotori.golababdiscord.domain.ranking.service;

import com.dotori.golababdiscord.domain.api.service.caller.ApiCaller;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RankingService {
    private final ApiCaller apiCaller;
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
    private final SogoBot sogoBot;

    public void sendResult2Weeks() {
        RequestRankingDto ranking = apiCaller.getRanking(0, 19);

        ReceiverDto receiver = new ReceiverDto(sogoBot.getVoteChannel());
        MessageDto message = messageViews.generateRankingMessage(ranking);

        messageSenderService.sendMessage(receiver, message);
    }
}
