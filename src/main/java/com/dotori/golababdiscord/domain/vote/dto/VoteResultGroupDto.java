package com.dotori.golababdiscord.domain.vote.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
public class VoteResultGroupDto {
    private final VoteResultDto breakfast;
    private final VoteResultDto lunch;
    private final VoteResultDto dinner;
}
