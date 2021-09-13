package com.dotori.golababdiscord.domain.discord.trigger;

public interface Trigger<T> {
    boolean checkTrigger(T target);
}
