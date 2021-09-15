package com.dotori.golababdiscord.domain.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDayMenuDto {
    private final ResponseMealMenuDto breakfast;
    private final ResponseMealMenuDto lunch;
    private final ResponseMealMenuDto dinner;
}
