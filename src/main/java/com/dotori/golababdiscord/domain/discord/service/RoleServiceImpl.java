package com.dotori.golababdiscord.domain.discord.service;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.discord.dto.RoleDto;
import com.dotori.golababdiscord.domain.permission.repository.PermissionRepository;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.domain.user.service.UserService;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Getter
public class RoleServiceImpl implements RoleService {
    private final PermissionRepository permissionRepository;
    private final UserService userService;
    private final SogoBot sogoBot;

    @Lazy
    public RoleServiceImpl(PermissionRepository permissionRepository, UserService userService, SogoBot sogoBot) {
        this.permissionRepository = permissionRepository;
        this.userService = userService;
        this.sogoBot = sogoBot;
    }

    @Override
    public Role createNewRole(RoleDto dto) {
        Guild officialGuild = sogoBot.getOfficialGuild();
        Role role = officialGuild.createRole()
                .setName(dto.getName())
                .setColor(dto.getColor())
                .setPermissions(dto.getPermissions())
                .complete();
        saveRole(dto, role.getIdLong());

        return role;
    }

    private void saveRole(RoleDto dto, Long id) {
        dto.setId(id);
        permissionRepository.save(dto.toEntity());
    }

    @Override
    public Role getRole(RoleDto dto) {
        if(!permissionRepository.existsByName(dto.getName()))
            return createNewRole(dto);
        Long id = permissionRepository.getByName(dto.getName()).getId();
        return sogoBot.getOfficialGuild().getRoleById(id);
    }

    @Override
    public void grantRole(UserDto userDto, RoleDto roleDto) {
        Role role = getRole(roleDto);
        User user = userService.getUser(userDto);

        Guild officialGuild = sogoBot.getOfficialGuild();
        Member member = officialGuild.retrieveMember(user).complete();

        officialGuild.addRoleToMember(member, role).complete();
    }
}
