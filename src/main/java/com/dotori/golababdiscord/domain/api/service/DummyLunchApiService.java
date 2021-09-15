package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.ResponseDayMenuDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseMealMenuDto;
import com.dotori.golababdiscord.domain.api.exception.MealTypeNotFoundException;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("dummy-lunch-api-service")
public class DummyLunchApiService implements LunchApiService{
    @Override
    public ResponseDayMenuDto getMealsToday() {
        return getResponseDayMenuDto();
    }

    private ResponseDayMenuDto getResponseDayMenuDto() {
        ResponseMealMenuDto breakfast = new ResponseMealMenuDto();
        breakfast.add("백미밥");
        breakfast.add("꽃게탕");
        breakfast.add("닭정육 통살 구이");
        breakfast.add("파전");
        breakfast.add("깍두기");
        ResponseMealMenuDto lunch = new ResponseMealMenuDto();
        lunch.add("참치 김치 볶음밥");
        lunch.add("미소 된장국");
        lunch.add("계란 말이");
        lunch.add("겉절이 김치");
        lunch.add("과일 주스");
        ResponseMealMenuDto dinner = new ResponseMealMenuDto();
        dinner.add("백미밥");
        dinner.add("육계장");
        dinner.add("삼치카레구이");
        dinner.add("메추리알 조림");
        dinner.add("갈비만두");
        dinner.add("초코우유");
        return new ResponseDayMenuDto(breakfast, lunch, dinner);
    }

    @Override
    public ResponseMealMenuDto getMealToday(MealType meal) {
        ResponseDayMenuDto menu = getResponseDayMenuDto();
        switch (meal) {
            case BREAKFAST:
                return menu.getBreakfast();
            case LUNCH:
                return menu.getLunch();
            case DINNER:
                return menu.getDinner();
            default:
                throw new MealTypeNotFoundException(meal);
        }
    }

    @Override
    public ResponseDayMenuDto getMealsByDay(Date date) {
        return getResponseDayMenuDto();
    }
}
