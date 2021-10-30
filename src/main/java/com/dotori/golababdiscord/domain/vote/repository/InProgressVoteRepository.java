package com.dotori.golababdiscord.domain.vote.repository;

import com.dotori.golababdiscord.domain.vote.entity.InProgressVote;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface InProgressVoteRepository extends JpaRepository<InProgressVote, Long> {
    InProgressVote getByVoteDateAndMeal(Date voteDate, MealType meal);
    boolean existsByVoteMessageId(Long voteMessageId);
}
