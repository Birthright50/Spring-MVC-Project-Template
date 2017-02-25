package com.birthright.service;

import com.birthright.entity.PasswordResetToken;
import com.birthright.entity.User;
import com.birthright.repository.PasswordResetTokenRepository;
import com.birthright.service.interfaces.IPasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by birthright on 26.02.17.
 */
@Service
public class PasswordResetTokenService implements IPasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Override
    public PasswordResetToken findPasswordResetToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public PasswordResetToken createPasswordResetToken(String token, User user) {
        return null;
    }

    @Override
    public void deletePasswordResetToken(String token) {

    }
}
