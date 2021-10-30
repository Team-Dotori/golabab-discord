package com.dotori.golababdiscord.domain.vote.exception;

import com.dotori.golababdiscord.domain.vote.dto.InProgressVoteDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
public class EmptyVoteGroupException extends RuntimeException {
    private final List<InProgressVoteDto> votes;
}
