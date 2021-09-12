package com.dotori.golababdiscord.domain.authorize.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UnValidatedUserDto {
    private final Long discordId;
    private final String name;

    public DomainValidatedUserDto toDomainValidateUserDto(String email, DepartmentType department) {
        return new DomainValidatedUserDto(discordId, name, email, department);
    }
}
