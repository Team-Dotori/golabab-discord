package com.dotori.golababdiscord.domain.user.service;

import com.dotori.golababdiscord.domain.authorize.enum_type.DepartmentType;
import com.dotori.golababdiscord.domain.user.exception.UserNotEnrolledException;
import com.dotori.golababdiscord.global.dto.UserDto;
import com.dotori.golababdiscord.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserDto getUser(User user) {
        System.out.println(user.getIdLong());
        com.dotori.golababdiscord.global.entity.User entity = userRepository.getById(user.getIdLong());
        if(entity == null) throw new UserNotEnrolledException();
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail(), DepartmentType.of(entity.getDepartmentType()));
    }
}
