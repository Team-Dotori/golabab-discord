package com.dotori.golababdiscord.domain.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseDayMenuDto {
    private final ResponseMealMenuDto breakfast;
    private final ResponseMealMenuDto lunch;
    private ResponseMealMenuDto dinner;
}
