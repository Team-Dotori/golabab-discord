package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.ResponseDayMenuDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseMealMenuDto;
import com.dotori.golababdiscord.domain.api.exception.ApiException;
import com.dotori.golababdiscord.domain.api.exception.LoadingLunchDataFailureException;
import com.dotori.golababdiscord.domain.api.exception.MealTypeNotFoundException;
import com.dotori.golababdiscord.domain.api.property.LunchApiProperty;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HardCodedLunchApiService implements LunchApiService {
    private final ApiService<String> apiService;
    private final LunchApiProperty lunchApiProperty;

//    @ApiEntry(method = HttpMethod.GET, resourcePath = "/api/v1/meals/get-meals-today")
    @Override
    public ResponseDayMenuDto getMealsToday() {
        ResponseEntity<String> response =
                apiService.get(lunchApiProperty.getBaseUrl() + ":" +  lunchApiProperty.getPort() + "/api/v1/meals/today", String.class);
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            String responseStr = response.getBody();
            ResponseDayMenuDto result = mapToDto(responseStr);
            return result;
        }
        else throw new ApiException(response.getStatusCode());
    }

    private ResponseDayMenuDto mapToDto(String responseStr) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Map<String, List<String>>> map;
        try {
            map = mapper.readValue(responseStr, Map.class);
        } catch (JsonProcessingException e) {
            throw new LoadingLunchDataFailureException(e);
        }
        Map<String, List<String>> meals = map.get("meals");
        return new ResponseDayMenuDto(
                new ResponseMealMenuDto(meals.get("breakfast")),
                new ResponseMealMenuDto(meals.get("lunch")),
                new ResponseMealMenuDto(meals.get("dinner"))
        );
    }

    @Override
    public ResponseMealMenuDto getMealToday(MealType meal) {
        switch(meal) {
            case BREAKFAST:
                return getMealsToday().getBreakfast();
            case LUNCH:
                return getMealsToday().getLunch();
            case DINNER:
                return getMealsToday().getDinner();
            default:
                throw new MealTypeNotFoundException(meal);
        }
    }

    @Override
    public ResponseDayMenuDto getMealsByDay(Date date) {
        //TODO 구현예정
        return getMealsToday();
    }
}
