package com.dotori.golababdiscord.domain.authorize.enum_type;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//인증이 실패한 이유를 정의하는 enum 타입
public enum FailureReason {
    ALREADY_ENROLLED, //이미 가입된 유저일경우
    DOMAIN_IS_NOT_SCHOOL_DOMAIN //입력한 이메일의 도메인이 학교 도메인이 아닐경우
}
