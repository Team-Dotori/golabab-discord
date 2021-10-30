package com.dotori.golababdiscord.domain.vote.enum_type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public enum MealType {
    BREAKFAST("조식"), LUNCH("중식"), DINNER("석식");
    
    private final String korean;
}
