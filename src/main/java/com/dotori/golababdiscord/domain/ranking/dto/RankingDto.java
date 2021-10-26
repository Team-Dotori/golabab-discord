package com.dotori.golababdiscord.domain.ranking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RankingDto {
    private final String menuName;
    private final Integer numOfVote;
}
