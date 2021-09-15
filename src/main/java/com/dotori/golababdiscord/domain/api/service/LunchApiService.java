package com.dotori.golababdiscord.domain.api.service;

import com.dotori.golababdiscord.domain.api.dto.ResponseDayMenuDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseMealMenuDto;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;

import java.util.Date;

public interface LunchApiService {
    ResponseDayMenuDto getMealsToday();
    ResponseMealMenuDto getMealToday(MealType meal);
    ResponseDayMenuDto getMealsByDay(Date date);
}
