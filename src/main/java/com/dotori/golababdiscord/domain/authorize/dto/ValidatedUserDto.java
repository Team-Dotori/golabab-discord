package com.dotori.golababdiscord.domain.authorize.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.global.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ValidatedUserDto {
    private final Long discordId;
    private final String name;
    private final String email;
    private final DepartmentType department;

    public UserDto toUserDto() {
        return new UserDto(this.discordId, this.name, this.email, this.department);
    }
}
