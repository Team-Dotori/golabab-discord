package com.dotori.golababdiscord.domain.discord.exception;

import com.dotori.golababdiscord.domain.authorize.enum_type.FailureReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UnknownFailureReasonException extends RuntimeException {
    private final FailureReason reason;
}
