package com.dotori.golababdiscord.domain.permission.scheduler;

import com.dotori.golababdiscord.domain.discord.dto.RoleDto;
import com.dotori.golababdiscord.domain.discord.service.RoleService;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionGrantSchedulerImpl implements PermissionGrantScheduler{
    private final RoleService roleService;
    private final List<UserDto> requireGrantRoleUsers = new ArrayList<>();

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void grant() {
        requireGrantRoleUsers.forEach(userDto -> {
            RoleDto roleDto = userDto.getPermission().getRole();
            removeLegacyRoles(userDto);
            roleService.grantRole(userDto, roleDto);
        });
    }

    private void removeLegacyRoles(UserDto userDto) {
        for (SogoPermission value : SogoPermission.values()) {
            roleService.removeRoleAtUser(userDto, value.getRole());
        }
    }

    @Override
    public void grantToUser(UserDto user) {
        requireGrantRoleUsers.add(user);
    }
}
