package com.dotori.golababdiscord.domain.vote.scheduler;

import com.dotori.golababdiscord.domain.vote.dto.*;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.dotori.golababdiscord.global.exception.DateParseFailureException;
import com.dotori.golababdiscord.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoteSchedulerImpl implements VoteScheduler{
    private final VoteService voteService;
    private static final String BREAKFAST_VOTE_CRON = "0 " + 40 + " " + 15 + " * * MON-FRI";
    private static final String LUNCH_VOTE_CRON = "0 " + 45 + " " + 15 + " * * MON-FRI";
    private static final String DINNER_VOTE_CRON = "0 " + 50 + " " + 15 + " * * MON-FRI";
    private static final String COLLECT_VOTE_CRON = "0 " + 51 + " " + 15 + " * * MON-FRI";

    @Override
    @Scheduled(cron=COLLECT_VOTE_CRON)
    public void collectVote() {
        Date today = getToday();

        InProgressVoteGroupDto group = createInProgressVoteGroupByDay(today);
        VoteResultGroupDto result = voteService.calculateVoteResult(group);

        voteService.closeVote(group);
        voteService.sendVoteResult(result); //is right result
    }
    private InProgressVoteGroupDto createInProgressVoteGroupByDay(Date to) {
        //TODO 금요일처럼 특정 투표가 진행되지 않았을경우
        InProgressVoteDto breakfast =
                voteService.getInProgressVote(to, MealType.BREAKFAST);
        InProgressVoteDto lunch =
                voteService.getInProgressVote(to, MealType.LUNCH);
        InProgressVoteDto dinner =
                voteService.getInProgressVote(to, MealType.DINNER);
        return new InProgressVoteGroupDto(breakfast, lunch, dinner);
    }
    private Date getToday() {
        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = transFormat.format(from);
        try {
            return transFormat.parse(date);
        } catch (ParseException e) {
            throw new DateParseFailureException();
        }
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
    /*
    @Scheduled(cron="20 * * * * *")
    public void openTestVote() {
        VoteDto dinnerVote = voteService.createNewVote(MealType.DINNER);
        InProgressVoteDto dinner = voteService.openVote(dinnerVote);

        saveInpProgressVote(dinner);
    }*/
}
