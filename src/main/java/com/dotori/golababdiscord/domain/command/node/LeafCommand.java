package com.dotori.golababdiscord.domain.command.node;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//커멘드중 최하위 커멘드에 대한 클래스
public abstract class LeafCommand extends Command{
    //기본 생성자
    public LeafCommand(String prefix) {
        super(prefix);
    }

    @Override//자식노드가 존재하지 않다고 보장되어있으므로 무조건 해당 커맨드의 실행메서드를 실행한다.
    public boolean execute(User user, MessageChannel channel, String args) {
        run(user, channel, args);
        return true;
    }
}
