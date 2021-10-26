package com.dotori.golababdiscord.domain.ranking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class RequestRankingDto {
    private final List<RankingDto> list;
}
