package com.birthright.repository;

import com.birthright.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Birthright on 28.04.2016.
 */
public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailOrUsername(String email, String username);

}
