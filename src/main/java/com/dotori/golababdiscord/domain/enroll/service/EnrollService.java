package com.dotori.golababdiscord.domain.enroll.service;

import com.dotori.golababdiscord.global.dto.UserDto;

public interface EnrollService {
    void enroll(UserDto user);

    void checkEnrollCondition(UserDto toUserDto);
}
