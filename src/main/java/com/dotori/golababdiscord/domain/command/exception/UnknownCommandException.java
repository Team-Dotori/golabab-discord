package com.dotori.golababdiscord.domain.command.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class UnknownCommandException extends RuntimeException {
    private final String args;
}
