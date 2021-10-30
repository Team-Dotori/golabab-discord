package com.dotori.golababdiscord.domain.discord.listeners.handler;

import net.dv8tion.jda.api.events.Event;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface Handler<T extends Event> {
    void handleEvent(T event);
}
