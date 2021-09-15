package com.dotori.golababdiscord.domain.vote.scheduler;

public interface VoteScheduler {
    void collectVote();
    void openBreakfastVote();
    void openLunchVote();
    void openDinnerVote();
}
