package com.dotori.golababdiscord.domain.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class RequestMealDto {
    private final Map<String, Integer> menus;

    public RequestMealDto() {
        menus = new HashMap<>();
    }

    public void addVote(String menu) {
        addVote(menu, 1);
    }

    public void addVote(String nameOfMenu, Integer numOfVote) {
        if(menus.containsKey(nameOfMenu))
            menus.replace(nameOfMenu, menus.get(nameOfMenu) + numOfVote);
        else menus.put(nameOfMenu, numOfVote);
    }
}
