package com.dotori.golababdiscord.domain.vote.service;

import com.dotori.golababdiscord.domain.vote.dto.*;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;

import java.util.Date;
import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface VoteService {
    VoteDto createNewVote(MealType meal);
    InProgressVoteDto openVote(VoteDto vote);
    VoteResultGroupDto calculateVoteResult(InProgressVoteGroupDto vote);
    void closeVote(InProgressVoteGroupDto vote);
    void sendVoteResult(VoteResultGroupDto result);
    void save(InProgressVoteDto inProgressVote);
    InProgressVoteDto getInProgressVote(Date to, MealType breakfast);
    boolean isVoteMessage(long messageIdLong);
    List<InProgressVoteDto> getInProgressVotes(Date today);
}
