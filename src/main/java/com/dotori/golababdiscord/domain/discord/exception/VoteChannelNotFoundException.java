package com.dotori.golababdiscord.domain.discord.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoteChannelNotFoundException extends RuntimeException {
    private final Long voteChannel;
}
