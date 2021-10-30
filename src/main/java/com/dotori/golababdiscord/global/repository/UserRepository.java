package com.dotori.golababdiscord.global.repository;

import com.dotori.golababdiscord.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsById(long id);
    User getById(long id);
}
