package com.dotori.golababdiscord.domain.discord.command.trigger;

public interface Trigger<T> {
    boolean checkTrigger(T target);
}
