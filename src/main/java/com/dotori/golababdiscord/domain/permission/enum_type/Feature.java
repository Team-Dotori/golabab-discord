package com.dotori.golababdiscord.domain.permission.enum_type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
public enum Feature {
    GOLABAB_VOTE("급식투표"),
    GOLABAB_MANAGE("급식투표 관리"),
    USER_PROMOTION("유저 승급");

    @Getter
    private final String display;
}
