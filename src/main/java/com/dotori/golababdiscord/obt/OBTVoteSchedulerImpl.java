package com.dotori.golababdiscord.obt;

import com.dotori.golababdiscord.domain.vote.dto.InProgressVoteDto;
import com.dotori.golababdiscord.domain.vote.dto.InProgressVoteGroupDto;
import com.dotori.golababdiscord.domain.vote.dto.VoteDto;
import com.dotori.golababdiscord.domain.vote.dto.VoteResultGroupDto;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import com.dotori.golababdiscord.domain.vote.service.VoteService;
import com.dotori.golababdiscord.global.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
@Slf4j
public class OBTVoteSchedulerImpl implements VoteScheduler {
    private final OBTDateUtils dateUtils;
    private final VoteService voteService;
    private final Date today;
    private static final int MINUTE = 60000;

    @Lazy
    public OBTVoteSchedulerImpl(OBTDateUtils dateUtils, VoteService voteService) {
        this.dateUtils = dateUtils;
        this.voteService = voteService;
        this.today = dateUtils.getToday();
    }

    @Override
    @Scheduled(initialDelay = MINUTE * 3, fixedRate = MINUTE *4)
    public void collectVote() {
        InProgressVoteGroupDto group = createInProgressVoteGroupByDay(today);
        VoteResultGroupDto result = voteService.calculateVoteResult(group);

        voteService.closeVote(group);
        voteService.sendVoteResult(result);
        dateUtils.nextDay();
    }
    private InProgressVoteGroupDto createInProgressVoteGroupByDay(Date today) {        ;
        return new InProgressVoteGroupDto(voteService.getInProgressVotes(today)/*breakfast, lunch, dinner*/);
    }

    @Override
    @Scheduled(initialDelay = 0, fixedRate = MINUTE *4)
    public void openBreakfastVote() {
        VoteDto breakfastVote = voteService.createNewVote(MealType.BREAKFAST);
        InProgressVoteDto breakfast = voteService.openVote(breakfastVote);

        saveInpProgressVote(breakfast);
    }
    private void saveInpProgressVote(InProgressVoteDto inProgressVote) {
        voteService.save(inProgressVote);
    }

    @Override
    @Scheduled(initialDelay = MINUTE, fixedRate = MINUTE *4)
    public void openLunchVote() {
        VoteDto lunchVote = voteService.createNewVote(MealType.LUNCH);
        InProgressVoteDto lunch = voteService.openVote(lunchVote);

        saveInpProgressVote(lunch);
    }

    @Override
    @Scheduled(initialDelay = MINUTE * 2, fixedRate = MINUTE *4)
    public void openDinnerVote() {
        VoteDto dinnerVote = voteService.createNewVote(MealType.DINNER);
        InProgressVoteDto dinner = voteService.openVote(dinnerVote);

        saveInpProgressVote(dinner);
    }
}
