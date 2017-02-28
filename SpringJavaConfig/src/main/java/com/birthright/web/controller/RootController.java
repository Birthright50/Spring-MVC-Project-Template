package com.birthright.web.controller;


import com.birthright.constants.Routes;
import com.birthright.entity.Role;
import com.birthright.entity.User;
import com.birthright.repository.RoleRepository;
import com.birthright.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

/**
 * Created by Birthright on 27.04.2016.
 */
@Controller
@Log4j2
public class RootController {
    private boolean start = false;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Show home page
     */
    @GetMapping(Routes.ROOT_URI)
    public String index() {
        if (!start) {
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));
            userRepository.save(User.builder().email("birthright5050@gmail.com")
                                        .password("uuiouiou").enabled(true).tokenExpired(false).username("Example")
                                        .roles(Collections.singletonList(roleRepository.findByName("USER"))).build());
            start = true;
        }
        return Routes.ROOT_VIEW;
    }
}
