package com.dotori.golababdiscord.domain.permission.repository;

import com.dotori.golababdiscord.domain.permission.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PermissionRepository extends JpaRepository<Role, Integer> {
    Role getByName(String name);
    boolean existsByName(String name);
    @Transactional
    void deleteById(Long id);
}
