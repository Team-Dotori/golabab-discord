package com.dotori.golababdiscord.domain.permission.repository;

import com.dotori.golababdiscord.domain.permission.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Role, Integer> {
    Role getByName(String name);
    boolean existsByName(String name);
}
