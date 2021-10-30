package com.dotori.golababdiscord.domain.authorize.service;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.UnValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.domain.authorize.exception.DepartmentNotFoundException;
import com.dotori.golababdiscord.domain.authorize.util.AuthTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//AuthorizeService 를 구현한 서비스 클래스
@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {
    private final AuthTokenUtils authTokenUtils; //인증토큰의 발급/복호화를 위한 유틸
    @Value("${server.address}")
    String host;//인증링크 생성을 위한 authorize 서버 주소
    @Value("${server.port}")
    String port;//인증링크 생성을 위한 authorize 서버 포트

    @Override //인증링크에 담긴 토큰을 받아서 이를 검증한 후 인증된 사용자 정보(ValidatedUserDto)를 반환하는 메서드
    public ValidatedUserDto validateAuthorizeLink(String token) {
        authTokenUtils.validateToken(token);//유틸을 통해 토큰을 검사한다
        return authTokenUtils.decodeToken(token);//검사중 예외가 발생하지 않을경우 토큰을 복호화한다
    }

    @Override //인증링크를 생성한 후 반환하는 메서드
    public String generateAuthorizeLink(DomainValidatedUserDto user) {
        String token = authTokenUtils.generateToken(user);//인증링크를 위한 토큰을 생성한다
        return generateAuthorizeLinkByToken(token);//위에서 생성한 토큰을 통해 인증링크를 생성하고 반환한다
    }

    //token 을 통해 형식에 맞게 인증링크를 생성하는 메서드
    private String generateAuthorizeLinkByToken(String token) {
        return host + ":" + port + "/authorize?token=" + token; //서버 주소, 포트, 인증기능 엔드포인트, 토큰 을 통해 인증링크를 구성한다
    }

    @Override //미인증 유저에게서 입력받은 이메일 도메인이 학교도메인인지 검사하고, 학교도메인이면 DomainValidatedUserDto 로 치환하여 반환하는 메서드
    public DomainValidatedUserDto validateDomain(UnValidatedUserDto user, String email) {
        String domain = getDomainFromEmail(email);//이메일의 도메인을 가져온다
        DepartmentType department = getDepartmentFromDomain(domain);//도메인에 해당하는 소속정보를 가져온다 (만약 해당하는 소속정보가 없으면 예외를 발생시킨다)
        return user.toDomainValidateUserDto(email, department);//가져온 소속정보를 바탕으로 DomainValidateUserDto 를 구성하여 반환한다
    }
    //이메일의 도메인을 추출하여 반환하는 메서드
    private String getDomainFromEmail(String email) {
        return email.substring(email.indexOf("@") + 1); //이메일의 구조는 XXX@YYY.ZZZ 이며 도메인은 YYY.ZZZ 이므로 @ 이후의 내용을 가져오면 된다
    }
    //도메인에 해당하는 소속정보를 가져오는 메서드
    private DepartmentType getDepartmentFromDomain(String domain) {
        //TODO 2021.10.30 DepartmentType.getByDomain(String domain) 메서드로 불러올 수 있도록 수정할것 JeeInho
        for (DepartmentType value : DepartmentType.values()) {//저장된 모든 소속정보를 불러와서 하나씩 iteration 한다
            if(value.getDomain().equals(domain))  return value;//도메인이 일치하면 해당 소속 정보를 반환
        } throw new DepartmentNotFoundException(domain);//소속 정보를 찾을 수 없을 때 DepartmentNotFoundException 을 발생시킨다
    }
}
