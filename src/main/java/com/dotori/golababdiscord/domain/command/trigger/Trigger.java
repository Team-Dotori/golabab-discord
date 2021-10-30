package com.dotori.golababdiscord.domain.command.trigger;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//트리거 클래스
public interface Trigger<T> {
    //트리거 조건을 체크하는 메소드
    boolean checkTrigger(T target);
}
