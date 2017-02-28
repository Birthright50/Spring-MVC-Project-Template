package com.birthright.service.interfaces;

import com.birthright.entity.PasswordResetToken;
import com.birthright.entity.VerificationToken;

/**
 * Created by birthright on 28.02.17.
 */
public interface ISecureService {
    String checkConfirmRegistrationToken(VerificationToken verificationToken, Long id);
    String checkConfirmResetPasswordToken(PasswordResetToken resetToken, Long id);
}
