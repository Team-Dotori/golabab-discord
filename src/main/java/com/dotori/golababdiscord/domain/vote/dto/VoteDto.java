package com.dotori.golababdiscord.domain.vote.dto;

import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class VoteDto {
    private final Date voteDate;
    private final MealType meal;
    private final List<String> menus;

    public InProgressVoteDto inProgress(Long voteMessageId) {
        return new InProgressVoteDto(voteDate, meal, menus, voteMessageId);
    }
}
