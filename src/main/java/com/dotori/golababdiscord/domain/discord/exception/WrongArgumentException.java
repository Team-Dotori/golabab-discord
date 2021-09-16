package com.dotori.golababdiscord.domain.discord.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WrongArgumentException extends RuntimeException {
    private final String args;
    private final String usage;
}
