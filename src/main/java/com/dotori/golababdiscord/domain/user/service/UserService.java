package com.dotori.golababdiscord.domain.user.service;

import com.dotori.golababdiscord.domain.user.dto.UserDto;
import net.dv8tion.jda.api.entities.User;

public interface UserService {
    UserDto getUserDto(User user);
    User getUser(UserDto user);
}
