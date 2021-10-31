package com.dotori.golababdiscord.domain.user.service;

import com.dotori.golababdiscord.domain.user.dto.UserDto;
import net.dv8tion.jda.api.entities.User;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface UserService {
    UserDto getUserDto(long userId);
    User getUser(UserDto user);
}
