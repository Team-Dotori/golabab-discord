package com.dotori.golababdiscord.domain.vote.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class InProgressVoteMemberDto {
    private final List<String> menus;
    private final long voteMessageId;

    public InProgressVoteMemberDto(InProgressVoteDto vote) {
        menus = vote.getMenus();
        voteMessageId = vote.getVoteMessageId();
    }
}
