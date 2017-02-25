package com.birthright.service.interfaces;

import com.birthright.entity.User;
import com.birthright.web.dto.UserDto;

/**
 * Created by birth on 19.02.2017.
 */
public interface IUserService {
    User createUserAccount(UserDto accountDto);

    String checkUserIsExists(String username, String email);

    User saveRegisteredUser(User user);

    User findUserByEmail(String email);

}