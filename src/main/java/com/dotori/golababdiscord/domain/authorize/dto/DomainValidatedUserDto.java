package com.dotori.golababdiscord.domain.authorize.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DomainValidatedUserDto {
    private final Long discordId;
    private final String name;
    private final String email;
    private final DepartmentType department;

    public ValidatedUserDto toValidatedUserDto() {
        return new ValidatedUserDto(discordId, name, email, department);
    }
}
