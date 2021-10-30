package com.dotori.golababdiscord.domain.command.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//입력된 채팅이 명령어가 아닐경우 발생하는 예외 클래스
@RequiredArgsConstructor @Getter
public class UnknownCommandException extends RuntimeException {
    private final String args;
}
