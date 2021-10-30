package com.dotori.golababdiscord.domain.discord.exception;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class UnknownFailureReasonException extends RuntimeException {
    private final FailureReason reason;
}
