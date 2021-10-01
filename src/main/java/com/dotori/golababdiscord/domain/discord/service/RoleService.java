package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.dto.RoleDto;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import net.dv8tion.jda.api.entities.Role;

public interface RoleService {
    Role createNewRole(RoleDto dto);
    Role getRole(RoleDto dto);
    Long getRoleId(RoleDto dto);

    void grantRole(UserDto userDto, RoleDto roleDto);
    void removeRoleAtUser(UserDto userDto, RoleDto role);

    void removeRole(RoleDto roleDto);
}
