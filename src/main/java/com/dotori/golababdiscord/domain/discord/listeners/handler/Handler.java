package com.dotori.golababdiscord.domain.discord.listeners.handler;

import net.dv8tion.jda.api.events.Event;

public interface Handler<T extends Event> {
    void handleEvent(T event);
}
