package com.bulbul.erp.repository;

import com.bulbul.erp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role getRoleByRoleName(String roleName);
}
