package com.dotori.golababdiscord.domain.authorize.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//이메일의 도메인이 검증된 유저의 정보를 담는 Dto
@RequiredArgsConstructor
@Getter
public class DomainValidatedUserDto {
    private final Long discordId; //해당 유저의 DiscordID
    private final String name; //해당 유저의 이름
    private final String email; //해당 유저의 추정 이메일 (인증과정에서 입력한 이메일)
    private final DepartmentType department; //해당 유저의 추정 소속 (추정 이메일의 도메인으로부터 알아낸 소속)

    //유저의 이메일이 완전히 검증되었을경우, ValidatedUserDto 로 치환하여 반환하는 메서드이다
    public ValidatedUserDto toValidatedUserDto() {
        return new ValidatedUserDto(discordId, name, email, department);
    }
}
