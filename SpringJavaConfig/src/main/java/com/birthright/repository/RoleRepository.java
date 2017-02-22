package com.birthright.repository;

import com.birthright.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by birth on 12.02.2017.
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
}
