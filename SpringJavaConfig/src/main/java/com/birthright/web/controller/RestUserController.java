package com.birthright.web.controller;

import com.birthright.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by birthright on 25.02.17.
 */
@RestController
public class RestUserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/registration/check_user")
    public String checkUserIfExists(@RequestParam String username,
                                    @RequestParam String email) {
        return userService.checkUserIsExists(username, email);
    }

}
