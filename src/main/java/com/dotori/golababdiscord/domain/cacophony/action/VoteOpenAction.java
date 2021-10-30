package com.dotori.golababdiscord.domain.cacophony.action;

import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Component
@RequiredArgsConstructor
public class VoteOpenAction {
    private final VoteScheduler voteScheduler;//투표 스케줄러
    public void openVote(MealType type) {
        switch (type) {
            case BREAKFAST -> voteScheduler.openBreakfastVote();
            case LUNCH -> voteScheduler.openLunchVote();
            case DINNER -> voteScheduler.openDinnerVote();
        }
    }
}
