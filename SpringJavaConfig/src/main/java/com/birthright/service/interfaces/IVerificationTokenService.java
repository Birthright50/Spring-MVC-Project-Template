package com.birthright.service.interfaces;

import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;

/**
 * Created by birthright on 25.02.17.
 */
public interface IVerificationTokenService {
    VerificationToken createNewVerificationToken(String existingToken);

    void deleteVerificationToken(VerificationToken verificationToken);

    VerificationToken createVerificationToken(User user, String token);

    VerificationToken findVerificationToken(String token);
}
