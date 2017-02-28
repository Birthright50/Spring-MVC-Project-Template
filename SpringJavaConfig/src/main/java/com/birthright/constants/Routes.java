package com.birthright.constants;

/**
 * Created by birthright on 28.02.17.
 */
public interface Routes {
    String ROOT_URI = "/";
    String ROOT_VIEW = "root/index";

    //Registration routes
    String REGISTRATION_URI = "/register";
    String REGISTRATION_VIEW = "register/index";
    String REGISTRATION_SUCCESS_URI = REGISTRATION_URI + "?success";
    String REGISTRATION_SUCCESS_VIEW = "register/success";
    String REGISTRATION_INFO_VIEW = "register/info";
    String REGISTRATION_RESEND_TOKEN_VIEW = "register/resend";


    String LOGIN_URI = "/login";
    String LOGIN_VIEW = "login/index";

    String LOGIN_INFO_URI = "/login/info";
    String LOGIN_INFO_VIEW = "login/info";

    String LOGIN_RESET_PASSWORD_VIEW = "login/reset_password";

    String LOGIN_NEW_PASSWORD_VIEW = "login/new_password";
    String LOGIN_NEW_PASSWORD_URI = "/login/new_password";

    String ERROR_URI = "/error";
    String ERROR_VIEW = "status/error";


}
