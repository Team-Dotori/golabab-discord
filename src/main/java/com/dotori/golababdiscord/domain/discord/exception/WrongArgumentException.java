package com.dotori.golababdiscord.domain.discord.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class WrongArgumentException extends RuntimeException {
    private final String args;
    private String usage;
}
