package com.dotori.golababdiscord.domain.permission.scheduler;

import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.user.dto.UserDto;

public interface PermissionGrantScheduler {
    void grant();
    void grantToUser(UserDto user);
}
