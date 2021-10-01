package com.dotori.golababdiscord.domain.discord.listeners.handler;

import com.dotori.golababdiscord.domain.discord.dto.RoleDto;
import com.dotori.golababdiscord.domain.discord.service.RoleService;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleRemoveHandler implements Handler<RoleDeleteEvent> {
    private final RoleService roleService;

    @Override
    public void handleEvent(RoleDeleteEvent event) {
        Role role = event.getRole();
        boolean isPermissionRole = Arrays.stream(SogoPermission.values())
                .anyMatch(permission -> roleService.getRoleId(permission.getRole()) == role.getIdLong());

        if(isPermissionRole)
            roleService.removeRole(new RoleDto(role.getName(), role.getColor()));
    }
}
