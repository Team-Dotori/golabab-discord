package com.dotori.golababdiscord.domain.permission.dto;

import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Getter
public class PermissionRoleDto {
    private final Long id;
    private final String name;
    private final SogoPermission permission;
}
