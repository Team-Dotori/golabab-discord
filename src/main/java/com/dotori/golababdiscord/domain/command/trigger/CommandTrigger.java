package com.dotori.golababdiscord.domain.command.trigger;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@EqualsAndHashCode
//커맨드 트리거(발동조건 명시) 클래스
public class CommandTrigger implements Trigger<String> {
    private final String prefix;//커맨드 의 접두어

    @Override//커맨드 트리거의 조건을 만족하는지 확인하는 메서드
    public boolean checkTrigger(String target) {
        return prefix.equals(target);//커맨드 의 접두어와 일치하는지 확인
    }
}
