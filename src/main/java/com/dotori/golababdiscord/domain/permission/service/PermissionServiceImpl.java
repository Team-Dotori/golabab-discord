package com.dotori.golababdiscord.domain.permission.service;

import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.permission.scheduler.PermissionGrantScheduler;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.domain.user.entity.User;
import com.dotori.golababdiscord.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{
    private final PermissionGrantScheduler permissionGrantScheduler;
    private final UserRepository userRepository;

    @Override
    public void grantPermission(UserDto user, SogoPermission permission) {
        //change permission at database
        User entity = userRepository.getById(user.getDiscordId());
        entity.setPermission(permission);
        userRepository.save(entity);
        //grant permission role
        UserDto permittedUser = entity.toDto();
        permissionGrantScheduler.grantToUser(permittedUser);
    }
}
