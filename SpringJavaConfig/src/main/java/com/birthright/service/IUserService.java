package com.birthright.service;

import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.web.dto.UserDto;

/**
 * Created by birth on 19.02.2017.
 */
public interface IUserService {
    User createUserAccount(UserDto accountDto);
    String checkUserIsExists(String username, String email);
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String token);
}
