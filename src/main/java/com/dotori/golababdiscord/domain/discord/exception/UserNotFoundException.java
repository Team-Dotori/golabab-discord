package com.dotori.golababdiscord.domain.discord.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private final Long id;
}
