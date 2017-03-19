package com.birthright.web.controller;


import com.birthright.constants.Routes;
import com.birthright.entity.User;
import com.birthright.service.interfaces.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Birthright on 27.04.2016.
 */
@Controller
@Log4j2
public class RootController {
    @Autowired
    private IUserService userService;
    /*
     * Show home page
     */
    @GetMapping(Routes.ROOT_URI)
    public String index(){
        User userByEmail = userService.findUserByEmail("birthright5050@gmail.com");
        userByEmail.setPassword("hui");
        userService.saveWithPasswordChanging(userByEmail);
        return Routes.ROOT_VIEW;
    }

}
