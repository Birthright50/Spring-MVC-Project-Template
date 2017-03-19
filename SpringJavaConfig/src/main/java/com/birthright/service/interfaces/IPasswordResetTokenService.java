package com.birthright.service.interfaces;

import com.birthright.entity.PasswordResetToken;
import com.birthright.entity.User;

/**
 * Created by birthright on 25.02.17.
 */
public interface IPasswordResetTokenService {
    PasswordResetToken findPasswordResetToken(String token);

    PasswordResetToken createPasswordResetToken(String token, User user);

    void deletePasswordResetToken(String token);

    void deletePasswordResetToken(User user);
}
