package com.birthright.repository;

import com.birthright.entity.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by birthright on 05.03.17.
 */
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {

    void deleteByUsername(String username);

}
