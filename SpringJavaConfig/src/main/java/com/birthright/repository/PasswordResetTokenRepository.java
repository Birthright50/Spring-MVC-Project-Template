package com.birthright.repository;

import com.birthright.entity.PasswordResetToken;
import com.birthright.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by birthright on 25.02.17.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(User user);
    void deleteByToken(String token);
    void deleteByUser(User user);
}
