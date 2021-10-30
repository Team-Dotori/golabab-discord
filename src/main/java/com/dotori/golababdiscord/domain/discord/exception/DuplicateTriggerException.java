package com.dotori.golababdiscord.domain.discord.exception;

import com.dotori.golababdiscord.domain.command.trigger.CommandTrigger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class DuplicateTriggerException extends RuntimeException {
    private final CommandTrigger trigger;
}
