package com.dotori.golababdiscord.domain.vote.scheduler;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface VoteScheduler {
    void collectVote();
    void openBreakfastVote();
    void openLunchVote();
    void openDinnerVote();
}
