package com.dotori.golababdiscord.domain.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
}
