package com.dotori.golababdiscord.domain.command.trigger;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class CommandTrigger implements Trigger<String> {
    private final String prefix;

    @Override
    public boolean checkTrigger(String target) {
        return prefix.equals(target);
    }
}
