package com.dotori.golababdiscord.domain.enroll.service;

import com.dotori.golababdiscord.domain.user.dto.UserDto;

public interface EnrollService {
    void enroll(UserDto user);

    void checkEnrollCondition(UserDto toUserDto);
}
