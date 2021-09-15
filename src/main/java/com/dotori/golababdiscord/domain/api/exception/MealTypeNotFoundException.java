package com.dotori.golababdiscord.domain.api.exception;

import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MealTypeNotFoundException extends RuntimeException {
    private final MealType meal;
}
