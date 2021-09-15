package com.dotori.golababdiscord.domain.vote.dto;

import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class InProgressVoteDto {
    private final Date voteDate;
    private final MealType meal;
    private final List<String> menus;
    private final long voteMessageId;
}
