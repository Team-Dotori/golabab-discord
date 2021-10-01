package com.dotori.golababdiscord.domain.api.dto;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ResponseMealMenuDto extends ArrayList<String> {
    public ResponseMealMenuDto(List<String> breakfast) {
        this.addAll(breakfast);
    }
}


