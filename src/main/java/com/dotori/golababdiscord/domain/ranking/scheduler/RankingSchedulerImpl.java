package com.dotori.golababdiscord.domain.ranking.scheduler;

import com.dotori.golababdiscord.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RankingSchedulerImpl implements RankingScheduler{
    private final RankingService rankingService;

    @Override @Scheduled(cron = "0 0 12 14,28 * *") //매일 12시마다 팁틱 게시
    public void sendRanking() {
        rankingService.sendResult2Weeks();
    }

    public static void main(String[] args) {
        String cronExpression = "0 0 12 14,28 * *";

        LocalDateTime time = LocalDateTime.now();
        while(true) {
            LocalDateTime next = CronExpression.parse(cronExpression).next(time);
            System.out.println(next);
            time = next;
        }
    }
}
