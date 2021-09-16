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
        if(args.equals("아침"))
            voteScheduler.openBreakfastVote();
        if(args.equals("점심"))
            voteScheduler.openLunchVote();
        if(args.equals("저녁"))
            voteScheduler.openDinnerVote();
        if(args.equals("집계"))
            voteScheduler.collectVote();
    }
}
