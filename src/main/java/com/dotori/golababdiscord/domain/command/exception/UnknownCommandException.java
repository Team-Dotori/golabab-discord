package com.dotori.golababdiscord.domain.command.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor @Getter
public class UnknownCommandException extends RuntimeException {
    private final String args;
}
