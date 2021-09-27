package com.dotori.golababdiscord.domain.permission.dto;

import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PermissionRoleDto {
    private final Long id;
    private final String name;
    private final SogoPermission permission;
}
