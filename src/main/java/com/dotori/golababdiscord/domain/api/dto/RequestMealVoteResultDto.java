package com.dotori.golababdiscord.domain.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class RequestMealVoteResultDto {
    private final Map<String, Integer> menu;

    public RequestMealVoteResultDto() {
        menu = new HashMap<>();
    }

    public void addVote(String menu) {
        addVote(menu, 1);
    }

    public void addVote(String nameOfMenu, Integer numOfVote) {
        if(menu.containsKey(nameOfMenu))
            menu.replace(nameOfMenu, menu.get(nameOfMenu) + numOfVote);
        else menu.put(nameOfMenu, numOfVote);
    }
}
