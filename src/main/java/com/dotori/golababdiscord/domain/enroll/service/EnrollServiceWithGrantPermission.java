package com.dotori.golababdiscord.domain.enroll.service;

import com.dotori.golababdiscord.domain.permission.enum_type.SogoPermission;
import com.dotori.golababdiscord.domain.permission.service.PermissionService;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.global.repository.UserRepository;
import org.springframework.stereotype.Service;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@Service
public class EnrollServiceWithGrantPermission extends EnrollServiceImpl{
    private final PermissionService permissionService;

    public EnrollServiceWithGrantPermission(UserRepository userRepository, PermissionService permissionService) {
        super(userRepository);
        this.permissionService = permissionService;
    }

    @Override
    public void enroll(UserDto user) {
        super.enroll(user);

        permissionService.grantPermission(user, SogoPermission.STUDENT);
    }
}
