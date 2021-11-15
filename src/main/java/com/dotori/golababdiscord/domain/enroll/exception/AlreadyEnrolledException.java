package com.dotori.golababdiscord.domain.enroll.exception;

import com.dotori.golababdiscord.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class AlreadyEnrolledException extends RuntimeException {
    private final UserDto user;
}
