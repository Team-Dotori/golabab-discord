package com.dotori.golababdiscord.domain.authorize.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//검증되지 않은 유저의 정보를 담는 DTO
@RequiredArgsConstructor
@Getter
public class UnValidatedUserDto {
    private final Long discordId;//해당 유저의 DiscordID
    private final String name;//해당 유저의 이름

    //유저의 추정 이메일의 도메인이 학교 도메인과 일치할경우(즉 학교 이메일일경우), 이메일과 소속정보를 인자로 받아 DomainValidatedUser 로 치환하여 반환하는 메서드이다
    public DomainValidatedUserDto toDomainValidateUserDto(String email, DepartmentType department) {
        return new DomainValidatedUserDto(discordId, name, email, department);
    }
}
