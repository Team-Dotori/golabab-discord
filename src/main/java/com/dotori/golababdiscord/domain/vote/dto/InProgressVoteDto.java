package com.dotori.golababdiscord.domain.vote.dto;

import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class InProgressVoteDto {
    private final Date voteDate;
    private final MealType meal;
    private final List<String> menus;
    private final long voteMessageId;
}
