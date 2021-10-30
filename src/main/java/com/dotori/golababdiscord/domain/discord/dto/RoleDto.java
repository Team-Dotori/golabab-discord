package com.dotori.golababdiscord.domain.discord.dto;

import com.dotori.golababdiscord.domain.permission.entity.Role;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.Permission;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Getter
public class RoleDto {
    @Setter
    private Long id;
    private final String name;
    private final List<Permission> permissions;
    private final Color color;

    public RoleDto(String name, Color color) {
        this.name = name;
        this.color = color;
        permissions = new ArrayList<>();
    }

    public RoleDto(String name, Color color, Permission... permissions) {
        this(name, color);
        addPermissions(permissions);
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }
    public void addPermissions(Permission... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
    }

    public Role toEntity() {
        return Role.builder()
                .id(id)
                .name(name)
                .build();
    }
}
