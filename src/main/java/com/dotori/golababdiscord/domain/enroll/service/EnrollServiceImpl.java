package com.dotori.golababdiscord.domain.enroll.service;

import com.dotori.golababdiscord.global.dto.UserDto;
import com.dotori.golababdiscord.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService{
    private final UserRepository userRepository;

    @Override
    public void enroll(UserDto user) {
        userRepository.save(user.toEntity());
    }
}
