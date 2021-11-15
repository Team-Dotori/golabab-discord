package com.dotori.golababdiscord.domain.user.service;

import com.dotori.golababdiscord.domain.discord.SogoBot;
import com.dotori.golababdiscord.domain.user.exception.UserNotEnrolledException;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final SogoBot sogoBot;

    @Override
    public UserDto getUserDto(long userId) {
        com.dotori.golababdiscord.domain.user.entity.User entity = userRepository.getById(userId);
        if(entity == null) throw new UserNotEnrolledException();
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getDepartmentType(), entity.getPermission());
    }

    @Override
    public User getUser(UserDto user) {
        return sogoBot.getUserById(user.getDiscordId());
    }
}
