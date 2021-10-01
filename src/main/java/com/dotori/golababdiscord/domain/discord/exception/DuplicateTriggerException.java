package com.dotori.golababdiscord.domain.discord.exception;

import com.dotori.golababdiscord.domain.discord.command.trigger.CommandTrigger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DuplicateTriggerException extends RuntimeException {
    private final CommandTrigger trigger;
}
