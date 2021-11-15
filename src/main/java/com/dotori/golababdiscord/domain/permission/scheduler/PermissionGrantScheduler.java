package com.dotori.golababdiscord.domain.permission.scheduler;

import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.dto.UserDto;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface PermissionGrantScheduler {
    void grant();
    void grantToUser(UserDto user);
}
