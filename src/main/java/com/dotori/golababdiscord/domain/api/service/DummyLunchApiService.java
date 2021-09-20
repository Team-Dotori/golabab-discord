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
        breakfast.add("프렌치토스트");
        breakfast.add("감자맑은국");
        breakfast.add("해물동그랑땡케찹");
        breakfast.add("배추김치");
        breakfast.add("콘푸로스트/초코첵스백색우유");
        ResponseMealMenuDto lunch = new ResponseMealMenuDto();
        lunch.add("백미밥");
        lunch.add("얼큰순대해장국");
        lunch.add("코다리무조림");
        lunch.add("청경채생채");
        lunch.add("깍두기");
        lunch.add("수제블루베리요거트");
        lunch.add("수제치킨/파채발사믹드레싱");
        ResponseMealMenuDto dinner = new ResponseMealMenuDto();
        //dinner.add("돈민지달걀볶음밥");
        //dinner.add("김치라면");
        //dinner.add("단무지쪽파무침");
        //dinner.add("모듬떡볶이김말이튀김");

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
