package com.birthright.service;

import com.birthright.entity.PasswordResetToken;
import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.service.interfaces.ISecureService;
import com.birthright.service.interfaces.IUserService;
import com.birthright.service.interfaces.IVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by birthright on 28.02.17.
 */
@Service
public class SecureService implements ISecureService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IVerificationTokenService tokenService;

    @Override
    public String checkConfirmRegistrationToken(VerificationToken verificationToken, Long id) {
        User user;
        if (verificationToken == null || !(user = verificationToken.getUser()).getId().equals(id)) {
            return "invalidToken";
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }
        user.setEnabled(true);
        userService.save(user, false);
        tokenService.deleteVerificationToken(verificationToken);
        return null;
    }

    @Override
    public String checkConfirmResetPasswordToken(PasswordResetToken resetToken, Long id) {
        if (resetToken == null || !resetToken.getUser().getId().equals(id)) {
            return "invalidToken";
        }
        Calendar calendar = Calendar.getInstance();
        if (resetToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return "expired";
        }
        return null;
    }

}
