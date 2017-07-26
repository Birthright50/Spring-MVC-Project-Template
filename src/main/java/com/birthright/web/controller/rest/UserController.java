package com.birthright.web.controller.rest;

import com.birthright.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by birthright on 25.02.17.
 */
@RestController
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/check_user")
    public String checkUserIfExists(@RequestParam String username, @RequestParam String email) {
        return userService.checkUserIsExists(username, email);
    }
}
