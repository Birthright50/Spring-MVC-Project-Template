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
@Transactional
public class VerificationEmailService implements IVerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    //self inject for transactions
    @Autowired
    private IVerificationTokenService verificationTokenService;

    @Autowired
    public VerificationEmailService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void deleteVerificationToken(VerificationToken verificationToken) {
        tokenRepository.delete(verificationToken);
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        return tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken createNewVerificationToken(String existingToken) {
        VerificationToken verificationToken = this.verificationTokenService.findVerificationToken(existingToken);
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
