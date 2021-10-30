package com.dotori.golababdiscord.domain.command.function;

import com.dotori.golababdiscord.domain.command.node.Command;
import com.dotori.golababdiscord.domain.vote.scheduler.VoteScheduler;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//강제적으로 투표를 개설하는 커맨드 클래스
public class VoteCommand extends Command {
    private final VoteScheduler voteScheduler;//투표 스케줄러

    //기본 생성자
    public VoteCommand(String prefix, VoteScheduler voteScheduler) {
        super(prefix);
        this.voteScheduler = voteScheduler;
    }

    @Override//커맨스 실행시 실행되는 메소드
    protected void run(User user, MessageChannel channel, String args) {
        switch (args) {//커맨드 인자를 확인하여 그에 맞는 작업을 수행한다
            case "아침": voteScheduler.openBreakfastVote();break;//아침 투표 개설
            case "점심": voteScheduler.openLunchVote();break;//점심 투표 개설
            case "저녁": voteScheduler.openDinnerVote();break;//저녁 투표 개설
            case "집계": voteScheduler.collectVote();break;//투표 결과 집계
        }
    }
}
