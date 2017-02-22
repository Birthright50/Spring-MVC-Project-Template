package com.birthright.repository;

import com.birthright.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Birthright on 28.04.2016.
 */
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailOrUsername(String email, String username);

}
