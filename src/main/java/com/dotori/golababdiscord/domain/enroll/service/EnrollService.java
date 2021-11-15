package com.dotori.golababdiscord.domain.enroll.service;

import com.dotori.golababdiscord.domain.user.dto.UserDto;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface EnrollService {
    void enroll(UserDto user);

    void checkEnrollCondition(UserDto toUserDto);
}
