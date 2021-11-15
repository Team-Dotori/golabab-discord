package com.dotori.golababdiscord.domain.discord.exception;

import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private final Long id;
}
