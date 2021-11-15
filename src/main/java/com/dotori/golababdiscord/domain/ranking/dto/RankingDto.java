package com.dotori.golababdiscord.domain.ranking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class RankingDto {
    private final String menuName;
    private final Integer numOfVote;
}
