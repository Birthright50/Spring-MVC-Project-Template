package com.birthright.web.controller;


import com.birthright.constants.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Birthright on 27.04.2016.
 */
@Controller
public class RootController {
    /*
     * Show home page
     */
    @GetMapping(Routes.ROOT_URI)
    public String index() throws InterruptedException {
        return Routes.ROOT_VIEW;
    }
}
