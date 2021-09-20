package com.dotori.golababdiscord.domain.api.dto;

import com.dotori.golababdiscord.domain.api.exception.MealTypeNotFoundException;
import com.dotori.golababdiscord.domain.vote.dto.VoteResultGroupDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDayVoteResultDto {
    private RequestMealVoteResultDto breakfast;
    private RequestMealVoteResultDto lunch;
    private RequestMealVoteResultDto dinner;

    public RequestDayVoteResultDto(VoteResultGroupDto result) {
        result.getMeal().forEach((meal, voteResult) -> {
            switch (meal) {
                case BREAKFAST:
                    breakfast = new RequestMealVoteResultDto(voteResult);
                    break;
                case LUNCH:
                    lunch = new RequestMealVoteResultDto(voteResult);
                    break;
                case DINNER:
                    dinner = new RequestMealVoteResultDto(voteResult);
                    break;
                default:
                    throw new MealTypeNotFoundException(meal);
            }
        });
    }
}
