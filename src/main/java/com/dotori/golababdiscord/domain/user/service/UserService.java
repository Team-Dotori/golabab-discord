package com.dotori.golababdiscord.domain.user.service;

import com.dotori.golababdiscord.global.dto.UserDto;
import net.dv8tion.jda.api.entities.User;

public interface UserService {
    UserDto getUser(User user);
}
