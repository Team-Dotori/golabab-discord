package com.dotori.golababdiscord.domain.enroll.service;

import com.dotori.golababdiscord.domain.enroll.exception.AlreadyEnrolledException;
import com.dotori.golababdiscord.domain.user.dto.UserDto;
import com.dotori.golababdiscord.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
//@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService{
    private final UserRepository userRepository;

    @Override
    public void enroll(UserDto user) {
        userRepository.save(user.toEntity());
    }

    @Override
    public void checkEnrollCondition(UserDto user) {
        if(userRepository.existsById(user.getDiscordId())) throw new AlreadyEnrolledException(user);
    }
}
