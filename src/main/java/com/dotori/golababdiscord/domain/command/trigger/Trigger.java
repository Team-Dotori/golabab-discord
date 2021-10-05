package com.dotori.golababdiscord.domain.command.trigger;

public interface Trigger<T> {
    boolean checkTrigger(T target);
}
