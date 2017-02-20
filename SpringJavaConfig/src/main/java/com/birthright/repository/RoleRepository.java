package com.birthright.repository;

import com.birthright.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by birth on 12.02.2017.
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor {
    Role findByName(String name);
}
