package com.birthright.repository.custom;

import com.birthright.entity.PersistentLogin;
import com.birthright.repository.PersistentLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by birthright on 05.03.17.
 */
@Transactional
@Repository
public class JpaTokenRepositoryImpl implements PersistentTokenRepository {
    @Autowired
    private PersistentLoginRepository persistentLoginRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        persistentLoginRepository.save(persistentLogin);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogin persistentLogin = persistentLoginRepository.findOne(series);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);
        persistentLoginRepository.save(persistentLogin);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLogin persistentLogin = persistentLoginRepository.findOne(seriesId);
        if (persistentLogin != null) {
            return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                                                 persistentLogin.getToken(), persistentLogin.getLastUsed());
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        try {
            persistentLoginRepository.deleteByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }

}
