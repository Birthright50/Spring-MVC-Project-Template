package com.birthright.service;

import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.repository.VerificationTokenRepository;
import com.birthright.service.interfaces.IVerificationTokenService;
import com.birthright.validation.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by birthright on 26.02.17.
 */
@Service
public class VerificationEmailService implements IVerificationTokenService {
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    @Transactional
    public void deleteVerificationToken(VerificationToken verificationToken) {
        tokenRepository.delete(verificationToken);
    }

    @Override
    @Transactional
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        return tokenRepository.save(myToken);
    }

    @Override
    @Transactional
    public VerificationToken createNewVerificationToken(String existingToken) throws TokenNotFoundException {
        VerificationToken verificationToken = findVerificationToken(existingToken);
        if (verificationToken == null) {
            throw new TokenNotFoundException();
        }
        verificationToken.updateToken(UUID.randomUUID().toString());
        return tokenRepository.save(verificationToken);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public VerificationToken findVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
