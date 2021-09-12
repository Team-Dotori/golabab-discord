package com.dotori.golababdiscord.global.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.global.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserDto {
    private final Long discordId;
    private final String name;
    private final String email;
    private final DepartmentType department;

    public User toEntity() {
        User user = new User();
        user.setId(discordId);
        user.setName(name);
        user.setEmail(email);
        user.setDepartmentType(department.name());

        return user;
    }
}
