package com.dotori.golababdiscord.domain.tiptic.scheduler;

import com.dotori.golababdiscord.domain.api.service.caller.ApiCaller;
import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.MessageDto;
import com.dotori.golababdiscord.domain.discord.dto.ReceiverDto;
import com.dotori.golababdiscord.domain.discord.service.MessageSenderService;
import com.dotori.golababdiscord.domain.discord.view.MessageViews;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class TipticSchedulerImpl implements TipticScheduler{
    private final ApiCaller apiCaller;
    private final MessageSenderService messageSenderService;
    private final MessageViews messageViews;
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

        ReceiverDto receiver = new ReceiverDto(sogoBot.getVoteChannel());
        MessageDto message = messageViews.generateTipticMessage(tiptic);

        messageSenderService.sendMessage(receiver, message);
    }
}
