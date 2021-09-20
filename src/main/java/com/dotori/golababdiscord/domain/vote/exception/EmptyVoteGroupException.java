package com.dotori.golababdiscord.domain.vote.exception;

import com.dotori.golababdiscord.domain.vote.dto.InProgressVoteDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EmptyVoteGroupException extends RuntimeException {
    private final List<InProgressVoteDto> votes;
}
