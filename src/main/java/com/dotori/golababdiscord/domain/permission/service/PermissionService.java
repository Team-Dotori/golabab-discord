package com.dotori.golababdiscord.domain.permission.service;

import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.dto.UserDto;

public interface PermissionService {
    void grantPermission(UserDto user, SogoPermission permission);
}
