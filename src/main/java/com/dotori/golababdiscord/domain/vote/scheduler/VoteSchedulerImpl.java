package com.dotori.golababdiscord.domain.vote.scheduler;

import com.dotori.golababdiscord.domain.vote.dto.*;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.dotori.golababdiscord.domain.vote.service.VoteService;
import com.dotori.golababdiscord.global.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoteSchedulerImpl implements VoteScheduler{
    private final DateUtils dateUtils;
    private final VoteService voteService;
    private static final String BREAKFAST_VOTE_CRON = "0 " + 20 + " " + 8 + " * * MON-FRI"; //오전 8시 20분
    private static final String LUNCH_VOTE_CRON = "0 " + 20 + " " + 13 + " * * MON-FRI"; //오후 1시 20분
    private static final String DINNER_VOTE_CRON = "0 " + 20 + " " + 19 + " * * MON-FRI"; //오후 7시 20분
    private static final String COLLECT_VOTE_CRON = "0 " + 0 + " " + 11 + " * * MON-FRI"; //오후 11시 00분

    @Override
    @Scheduled(cron=COLLECT_VOTE_CRON)
    public void collectVote() {
        Date today = dateUtils.getToday();

        InProgressVoteGroupDto group = createInProgressVoteGroupByDay(today);
        VoteResultGroupDto result = voteService.calculateVoteResult(group);

        voteService.closeVote(group);
        voteService.sendVoteResult(result);
    }
    private InProgressVoteGroupDto createInProgressVoteGroupByDay(Date today) {
        return new InProgressVoteGroupDto(voteService.getInProgressVotes(today)/*breakfast, lunch, dinner*/);
    }

    @Override
    @Scheduled(cron=BREAKFAST_VOTE_CRON)
    public void openBreakfastVote() {
        VoteDto breakfastVote = voteService.createNewVote(MealType.BREAKFAST);
        InProgressVoteDto breakfast = voteService.openVote(breakfastVote);

        saveInpProgressVote(breakfast);
    }
    private void saveInpProgressVote(InProgressVoteDto inProgressVote) {
        log.info(inProgressVote.getMeal() + "\n아이디 : " + inProgressVote.getVoteMessageId() + "\n메뉴 : " +
                Arrays.toString(inProgressVote.getMenus().toArray()));
        voteService.save(inProgressVote);
    }

    @Override
    @Scheduled(cron=LUNCH_VOTE_CRON)
    public void openLunchVote() {
        VoteDto lunchVote = voteService.createNewVote(MealType.LUNCH);
        InProgressVoteDto lunch = voteService.openVote(lunchVote);

        saveInpProgressVote(lunch);
    }

    @Override
    @Scheduled(cron=DINNER_VOTE_CRON)
    public void openDinnerVote() {
        VoteDto dinnerVote = voteService.createNewVote(MealType.DINNER);
        InProgressVoteDto dinner = voteService.openVote(dinnerVote);

        saveInpProgressVote(dinner);
    }
}
