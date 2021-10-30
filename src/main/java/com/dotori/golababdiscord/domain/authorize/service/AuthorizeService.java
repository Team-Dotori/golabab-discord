package com.dotori.golababdiscord.domain.authorize.service;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.UnValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//인증로직을 처리하는 서비스 클래스
public interface AuthorizeService {
    //authorize 엔드포인트를 통해 들어온 token 을 검증하여 추정이메일이 해당 유저의 소유임을 확인하고,
    // 증명완료시 ValidatedUserDto를 반환하는 메서드
    ValidatedUserDto validateAuthorizeLink(String token);
    //DomainValidatedUserDto 를 통해 인증링크를 발급하는 메서드
    String generateAuthorizeLink(DomainValidatedUserDto user);
    //UnValidateUserDto 와 입력된 이메일을 통해 도메인이 학교도메인인지 확인하는 메서드
    DomainValidatedUserDto validateDomain(UnValidatedUserDto user, String email);
}
