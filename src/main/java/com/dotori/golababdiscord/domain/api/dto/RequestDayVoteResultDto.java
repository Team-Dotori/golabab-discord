package com.dotori.golababdiscord.domain.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestDayVoteResultDto {
    private final RequestMealVoteResultDto breakfast;
    private final RequestMealVoteResultDto lunch;
    private final RequestMealVoteResultDto dinner;
}
