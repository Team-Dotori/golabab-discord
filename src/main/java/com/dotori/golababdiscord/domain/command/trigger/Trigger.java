package com.dotori.golababdiscord.domain.command.trigger;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface Trigger<T> {
    boolean checkTrigger(T target);
}
