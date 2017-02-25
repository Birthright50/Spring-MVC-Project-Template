package com.birthright.repository;

import com.birthright.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by birthright on 25.02.17.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

}
