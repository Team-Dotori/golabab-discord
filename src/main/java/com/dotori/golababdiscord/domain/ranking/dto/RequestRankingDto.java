package com.dotori.golababdiscord.domain.ranking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class RequestRankingDto {
    private final List<RankingDto> list;
}
