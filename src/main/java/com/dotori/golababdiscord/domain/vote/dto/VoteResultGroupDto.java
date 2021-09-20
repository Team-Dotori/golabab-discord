package com.dotori.golababdiscord.domain.vote.dto;

import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequiredArgsConstructor
@Getter
public class VoteResultGroupDto {
    private final HashMap<MealType, VoteResultDto> meal;

    public VoteResultGroupDto() {
        this.meal = new HashMap<>();
    }

    public void put(MealType key, VoteResultDto value) {
        meal.put(key, value);
    }
}
