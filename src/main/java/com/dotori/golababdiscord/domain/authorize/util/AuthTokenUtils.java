package com.dotori.golababdiscord.domain.authorize.util;

import com.dotori.golababdiscord.domain.authorize.dto.DomainValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.dto.ValidatedUserDto;
import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.global.property.JwtProperty;
import com.dotori.golababdiscord.global.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//인증토큰을 발급/복호화 하는 유틸 클래스
@Component
@RequiredArgsConstructor
public class AuthTokenUtils implements JwtUtils<DomainValidatedUserDto, ValidatedUserDto> {
    private final JwtProperty jwtProperty; //jwt 토큰에 대한 설정 정보

    @Override //토큰을 발급하는 메서드
    public String generateToken(DomainValidatedUserDto user) {
        Date now = new Date(); //현재 시간을 저장한다

        //JwtBuilder 를 통해 토큰을 만들고 반환한다
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)//헤더 설정
                .setIssuer(jwtProperty.getIssuer())//발급자 설정
                .setIssuedAt(now)//발급 시간 설정 (현재 시간)
                .setExpiration(getExpiration(now))//만료 시간 설정 (현재 시간 + 10분)
                .addClaims(getClaimsByUser(user))//사용자 정보 설정
                .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecret())//서명 알고리즘과 서명키 설정
                .compact();//토큰 생성
    }
    //사용자 정보를 토큰에 설정하는 메서드
    private Map<String, Object> getClaimsByUser(DomainValidatedUserDto user) {
        Map<String, Object> map = new HashMap<>();//사용자 정보를 저장할 Map

        map.put("id", user.getDiscordId());//사용자 아이디
        map.put("name", user.getName());//사용자 이름
        map.put("email", user.getEmail());//사용자 이메일
        map.put("department", user.getDepartment().name());//사용자 소속정보

        return map; //사용자 정보를 반환한다
    }
    //토큰의 만료 시간을 계산하는 메서드
    private Date getExpiration(Date issuedAt) {
        return new Date(issuedAt.getTime() + Duration.ofMinutes(10).toMillis()); //토큰 만료 시간은 토큰 발급 시간 + 10분이다
    }

    @Override //토큰의 무결성을 검증하는 메서드
    public void validateToken(String token) {
        if(token == null) throw new IllegalArgumentException("token is null"); //토큰이 null이면 예외를 발생시킨다
        Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token); //서명정보가 일치하는지 검사한다
        //TODO 2021.10.30 Claims 내에 유저 정보(id, name, email, department) 가 전부 존재하는지 검사하는 로직 작성 jeeInho
    }

    @Override //토큰을 복호화하는 메서드
    public ValidatedUserDto decodeToken(String token) {
        Claims claims = getClaimsByToken(token);//토큰을 통해 Claim을 가져온다
        return getUserByClaims(claims);//Claim을 통해 User를 가져온다
    }
    //토큰을 통해 Claim을 가져오는 메서드
    private Claims getClaimsByToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token).getBody(); //서명을 통해 토큰을 복호화하여 claim을 가져온다
    }
    //Claim을 통해 User를 가져온다
    private ValidatedUserDto getUserByClaims(Claims data) {
        //Claim 에 존재하는 데이터들을 통해 ValidatedUSerDto 를 구성하여 반환한다
        return new ValidatedUserDto(
                data.get("id", Long.class),//사용자 아이디
                data.get("name", String.class),//사용자 이름
                data.get("email", String.class),//사용자 이메일
                DepartmentType.of(data.get("department", String.class))//사용자 소속정보
        );
    }
}
