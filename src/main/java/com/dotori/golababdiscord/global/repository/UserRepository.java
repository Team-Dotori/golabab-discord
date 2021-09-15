package com.dotori.golababdiscord.global.repository;

import com.dotori.golababdiscord.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsById(long id);
    User getById(long id);
}
