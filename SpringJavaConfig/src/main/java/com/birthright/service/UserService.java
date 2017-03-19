package com.birthright.service;


import com.birthright.entity.User;
import com.birthright.enumiration.Role;
import com.birthright.repository.RoleRepository;
import com.birthright.repository.UserRepository;
import com.birthright.service.interfaces.IUserService;
import com.birthright.util.UserDetailsImpl;
import com.birthright.validation.UserAlreadyExistException;
import com.birthright.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Created by Birthright on 02.05.2016.
 */
@Service
public class UserService implements UserDetailsService, IUserService {
    private static final String OK = "OK";
    private static final String EMAIL_EXISTS = "EMAIL_EXISTS";
    private static final String USERNAME_EXISTS = "USERNAME_EXISTS";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUserAccount(UserDto accountDto) {
        User registered;
        try {
            registered = registerNewUserAccount(accountDto);
        } catch (UserAlreadyExistException e) {
            return null;
        }
        return registered;
    }

    private User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        String result = checkUserIsExists(userDto.getUsername(), userDto.getEmail());
        switch (result) {
            case EMAIL_EXISTS:
                throw new UserAlreadyExistException("Account with this email already exists" + userDto.getEmail());
            case USERNAME_EXISTS:
                throw new UserAlreadyExistException("Account with this username already exists" + userDto.getUsername());
        }
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .roles(Collections.singletonList(roleRepository.findByName(Role.USER.name())))
                .build();
        return saveWithPasswordChanging(user);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String checkUserIsExists(String username, String email) {
        User user = userRepository.findByEmailOrUsername(email, username);
        if (user == null) {
            return OK;
        }
        if (user.getEmail().equals(email)) {
            return EMAIL_EXISTS;
        }
        return USERNAME_EXISTS;
    }


    //For Spring Security
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        User user = Pattern.compile(EMAIL_PATTERN).matcher(login).matches() ?
                userRepository.findByEmail(login) : userRepository.findByUsername(login);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with this email/username: " + login);
        }
        return new UserDetailsImpl(user);
    }


    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User changeUserPassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User saveWithPasswordChanging(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
