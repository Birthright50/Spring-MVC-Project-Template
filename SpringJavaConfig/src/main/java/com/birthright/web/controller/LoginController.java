package com.birthright.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Birthright on 01.05.2016.
 */
//  UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
@Controller
public class LoginController {

    @GetMapping("/login")
    public String index() {
        return "login/index";
    }

}
