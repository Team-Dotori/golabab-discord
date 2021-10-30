package com.dotori.golababdiscord.domain.discord.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class VoteChannelNotFoundException extends RuntimeException {
    private final Long voteChannel;
}
