package com.dotori.golababdiscord.domain.vote.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoteEmojiNotFoundException extends RuntimeException {
    private final Integer id;
}
