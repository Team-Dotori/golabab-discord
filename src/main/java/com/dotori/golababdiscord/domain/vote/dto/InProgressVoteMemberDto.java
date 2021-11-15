package com.dotori.golababdiscord.domain.vote.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
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
