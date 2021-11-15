package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.RoleDto;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import net.dv8tion.jda.api.entities.Role;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface RoleService {
    Role createNewRole(RoleDto dto);
    Role getRole(RoleDto dto);
    Long getRoleId(RoleDto dto);

    void grantRole(UserDto userDto, RoleDto roleDto);
    void removeRoleAtUser(UserDto userDto, RoleDto role);

    void removeRole(RoleDto roleDto);
}
