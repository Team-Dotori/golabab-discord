package com.dotori.golababdiscord.obt;

import com.dotori.golababdiscord.domain.api.dto.ResponseDayMenuDto;
import com.dotori.golababdiscord.domain.api.dto.ResponseMealMenuDto;
import com.dotori.golababdiscord.domain.api.service.LunchApiService;
import com.dotori.golababdiscord.domain.vote.enum_type.MealType;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class OBTLunchApiService implements LunchApiService {
    private static final String[] RICES = new String[]{"백미밥", "흑미밥", "참치김치볶음밥", "전주비빔밥", "단팥죽", "새우크리미죽", "참치마요", "치킨마요", "차조밥", "오므라이스", "차슈덮밥"};
    private static final String[] SOUP = new String[]{"콩나물김칫국", "소고기무국", "만둣국", "돼지고기김치찌개", "참치김치찌개", "계란국", "소고기미역국", "토란국", "동태찌개", "추어탕", "오리탕", "바지락칼국수", "김치라면"};
    private static final String[] SIDE_DISH = new String[]{"콩자반", "새송이버섯장조림", "메추리알장조림", "어묵볶음", "김치볶음", "계란장조림", "미역줄기볶음", "무말랭이무침",
            "멸치볶음", "고추장멸치볶음", "두부조림", "깻잎김치", "꽈리고추볶음", "마늘쫑무침", "순두부"};
    private static final String[] MAIN_DISH = new String[]{"제육볶음", "상추튀김", "갈치구이", "고등어조림", "너비아니구이", "닭정육통살구이",
            "닭칠리볶음", "수육보쌈", "스팸곤약조림", "스팸계란말이", "대파불고기", "고구마치즈돈가스"};
    private static final String[] DESSERT = new String[]{"핫도그", "회오리감자", "베스킨라빈스아이스크림", "레몬에이드", "골라먹는과일쥬스", "전주수제초코마카롱", 
            "미니도넛", "사과롤케익", "크로와상", "비요뜨", "떠먹는 요구르트", "블루베리요거트"};

    @Override
    public ResponseDayMenuDto getMealsToday() {
        ResponseMealMenuDto breakfast = getRandomMeal();
        ResponseMealMenuDto lunch = getRandomMeal();
        ResponseMealMenuDto dinner = getRandomMeal();
        return new ResponseDayMenuDto(
                breakfast,
                lunch,
                dinner
        );
    }

    @Override
    public ResponseMealMenuDto getMealToday(MealType meal) {
        return getRandomMeal();
    }

    @Override
    public ResponseDayMenuDto getMealsByDay(Date date) {
        ResponseMealMenuDto breakfast = getRandomMeal();
        ResponseMealMenuDto lunch = getRandomMeal();
        ResponseMealMenuDto dinner = getRandomMeal();
        return new ResponseDayMenuDto(
                breakfast,
                lunch,
                dinner
        );
    }

    private ResponseMealMenuDto getRandomMeal() {
        Random random = new Random();
        ResponseMealMenuDto dto = new ResponseMealMenuDto();
        dto.add(RICES[random.nextInt(RICES.length)]);
        dto.add(SOUP[random.nextInt(SOUP.length)]);
        dto.add(SIDE_DISH[random.nextInt(SIDE_DISH.length)]);
        dto.add(MAIN_DISH[random.nextInt(MAIN_DISH.length)]);
        dto.add(MAIN_DISH[random.nextInt(MAIN_DISH.length)]);
        dto.add(DESSERT[random.nextInt(DESSERT.length)]);

        return dto;
    }
}
