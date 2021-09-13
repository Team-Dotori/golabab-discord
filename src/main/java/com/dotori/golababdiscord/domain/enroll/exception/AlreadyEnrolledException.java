package com.dotori.golababdiscord.domain.enroll.exception;

import com.dotori.golababdiscord.global.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AlreadyEnrolledException extends RuntimeException {
    private final UserDto user;
}
