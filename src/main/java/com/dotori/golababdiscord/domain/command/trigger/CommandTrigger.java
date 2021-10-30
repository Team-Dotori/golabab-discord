package com.dotori.golababdiscord.domain.command.trigger;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class CommandTrigger implements Trigger<String> {
    private final String prefix;

    @Override
    public boolean checkTrigger(String target) {
        return prefix.equals(target);
    }
}
