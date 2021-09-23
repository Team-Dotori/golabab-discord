package com.dotori.golababdiscord.domain.tiptic.scheduler;

import com.dotori.golababdiscord.domain.api.service.TipticApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

//@Component
@RequiredArgsConstructor
public abstract class TipticSchedulerImpl implements TipticScheduler{
    private final TipticApiService tipticApiService;
    private static final int percentage = 30;

    @Override @Scheduled(cron = "0 0 12 * * *") //매일 12시마다 팁틱 게시
    public void trySendTipticMessage() {
        if((random100()) < percentage) sendTipticMessage();
    }

    private int random100() {
        return new Random().nextInt(100) + 1;
    }

    private void sendTipticMessage() {
        String tiptic = tipticApiService.getImproveMessage();
        sendTiptic(tiptic);
    }

    protected abstract void sendTiptic(String tiptic);
}
