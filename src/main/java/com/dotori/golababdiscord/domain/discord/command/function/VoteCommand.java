package com.dotori.golababdiscord.domain.discord.command.function;

import com.dotori.golababdiscord.domain.discord.command.LeafCommand;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class VoteCommand extends LeafCommand {
    private final VoteScheduler voteScheduler;

    public VoteCommand(String prefix, VoteScheduler voteScheduler) {
        super(prefix);
        this.voteScheduler = voteScheduler;
    }

    @Override
    protected void run(User user, MessageChannel channel, String args) {
        try {
            voteScheduler.openBreakfastVote();
            voteScheduler.openLunchVote();
            voteScheduler.openDinnerVote();
            Thread.sleep(1000 * 60);
            voteScheduler.collectVote();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
