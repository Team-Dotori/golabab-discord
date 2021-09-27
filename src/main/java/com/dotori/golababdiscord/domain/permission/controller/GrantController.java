package com.dotori.golababdiscord.domain.permission.controller;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.permission.scheduler.PermissionGrantScheduler;
import com.dotori.golababdiscord.domain.permission.service.PermissionService;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GrantController {
    private final UserService userService;
    private final PermissionService permissionService;
    private final SogoBot sogoBot;
    @GetMapping("/grant-permission")
    public void grantPermission(@RequestParam SogoPermission permission, @RequestParam Long discordId) {
        UserDto user = userService.getUserDto(sogoBot.getUserById(discordId));
        permissionService.grantPermission(user, permission);
    }
}
