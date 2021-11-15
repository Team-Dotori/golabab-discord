package com.dotori.golababdiscord.domain.permission.repository;

import com.dotori.golababdiscord.domain.permission.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
public interface PermissionRepository extends JpaRepository<Role, Integer> {
    Role getByName(String name);
    boolean existsByName(String name);
    @Transactional
    void deleteById(Long id);
}
