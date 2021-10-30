package com.dotori.golababdiscord.domain.ranking.scheduler;

import com.dotori.golababdiscord.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class RankingSchedulerImpl implements RankingScheduler{
    private final RankingService rankingService;

    @Override @Scheduled(cron = "0 0 12 14,28 * *") //매일 12시마다 팁틱 게시
    public void sendRanking() {
        rankingService.sendResult2Weeks();
    }
}
