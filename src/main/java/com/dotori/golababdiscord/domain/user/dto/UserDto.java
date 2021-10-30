package com.dotori.golababdiscord.domain.user.dto;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class UserDto {
    private final Long discordId;
    private final String name;
    private final String email;
    private final DepartmentType department;
    private final SogoPermission permission;

    public User toEntity() {
        User user = new User();
        user.setId(discordId);
        user.setName(name);
        user.setEmail(email);
        user.setDepartmentType(department);
        user.setPermission(permission);

        return user;
    }
}
