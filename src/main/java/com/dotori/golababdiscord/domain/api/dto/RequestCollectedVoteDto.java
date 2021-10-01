package com.dotori.golababdiscord.domain.api.dto;

import com.dotori.golababdiscord.domain.api.exception.MealTypeNotFoundException;
import com.dotori.golababdiscord.domain.vote.dto.VoteResultGroupDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestCollectedVoteDto {
    private RequestMealDto breakfast;
    private RequestMealDto lunch;
    private RequestMealDto dinner;

    public RequestCollectedVoteDto(VoteResultGroupDto result) {
        result.getMeal().forEach((meal, voteResult) -> {
            switch (meal) {
                case BREAKFAST:
                    breakfast = new RequestMealDto(voteResult);
                    break;
                case LUNCH:
                    lunch = new RequestMealDto(voteResult);
                    break;
                case DINNER:
                    dinner = new RequestMealDto(voteResult);
                    break;
                default:
                    throw new MealTypeNotFoundException(meal);
            }
        });
    }
}
