package com.dotori.golababdiscord.domain.discord.command.function;

import com.dotori.golababdiscord.domain.discord.command.node.Command;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class VoteCommand extends Command {
    private final VoteScheduler voteScheduler;

    public VoteCommand(String prefix, VoteScheduler voteScheduler) {
        super(prefix);
        this.voteScheduler = voteScheduler;
    }

    @Override
    protected void run(User user, MessageChannel channel, String args) {
        switch (args) {
            case "아침": voteScheduler.openBreakfastVote();break;
            case "점심": voteScheduler.openLunchVote();break;
            case "저녁": voteScheduler.openDinnerVote();break;
            case "집계": voteScheduler.collectVote();break;
        }
    }
}
