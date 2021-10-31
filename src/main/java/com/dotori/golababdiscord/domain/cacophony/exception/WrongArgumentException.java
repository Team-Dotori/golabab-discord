package com.dotori.golababdiscord.domain.cacophony.exception;

import io.github.key_del_jeeinho.cacophony_lib.domain.command.component.Argument;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Getter
@RequiredArgsConstructor
public class WrongArgumentException extends RuntimeException {
    private final Argument argument;
    private final String usage;
}
