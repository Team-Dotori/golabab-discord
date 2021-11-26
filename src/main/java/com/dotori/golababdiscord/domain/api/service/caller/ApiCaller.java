package com.dotori.golababdiscord.domain.api.service.caller;

import com.dotori.golababdiscord.domain.api.dto.RequestCollectedVoteDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseDayMenuDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseMealMenuDto;
import com.dotori.golababdiscord.domain.api.service.*;
import com.dotori.golababdiscord.domain.logger.annotation.ApiEntry;
import com.dotori.golababdiscord.domain.ranking.dto.RequestRankingDto;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ApiCaller {
    private final LunchApiService lunchApiService;
    private final VoteApiService voteApiService;
    private final TipticApiService tipticApiService;
    private final ViewerApiService viewerApiService;

    @ApiEntry(apiName = "lunch_api", actionName = "get_meals_today", request = Void.class, response = ResponseDayMenuDto.class)
    public ResponseDayMenuDto getMealsToday() {
        System.out.println(lunchApiService.getMealsToday());
        System.out.println(lunchApiService.getMealsToday().getBreakfast());
        return lunchApiService.getMealsToday();
    }

    @ApiEntry(apiName = "lunch_api", actionName = "get_meal_today", request = MealType.class, response = ResponseMealMenuDto.class)
    public ResponseMealMenuDto getMealToday(MealType meal) {
        return lunchApiService.getMealToday(meal);
    }

    @ApiEntry(apiName = "lunch_api", actionName = "get_meals_by_day", request = Date.class, response = ResponseDayMenuDto.class)
    public ResponseDayMenuDto getMealsByDay(Date date) {
        return lunchApiService.getMealsByDay(date);
    }

    @ApiEntry(apiName = "tiptic_api", actionName = "get_improve_message", request = Void.class, response = String.class)
    public String getImproveMessage() {
        return tipticApiService.getImproveMessage();
    }

    @ApiEntry(apiName = "vote_api", actionName = "collect_total_vote_at_day", request = RequestCollectedVoteDto.class, response = Void.class)
    public void collectTotalVoteAtDay(RequestCollectedVoteDto result) {
        voteApiService.collectTotalVoteAtDay(result);
    }

    @ApiEntry(apiName = "viewer_api", actionName = "menu_ranking", request = Void.class, response = RequestRankingDto.class)
    public RequestRankingDto getRanking(int start, int end) {
        return viewerApiService.getRanking(start, end);
    }
}
