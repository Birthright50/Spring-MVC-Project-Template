package com.birthright.repository;

import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by birth on 18.02.2017.
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
  
    VerificationToken findByUser(User user);
}
