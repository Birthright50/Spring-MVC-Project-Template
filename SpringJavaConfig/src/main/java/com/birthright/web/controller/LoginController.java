package com.birthright.web.controller;


import com.birthright.entity.PasswordResetToken;
import com.birthright.entity.User;
import com.birthright.helper.AppHelper;
import com.birthright.helper.CreateEmailMessage;
import com.birthright.service.interfaces.IPasswordResetTokenService;
import com.birthright.service.interfaces.IUserService;
import com.birthright.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Birthright on 01.05.2016.
 */
//  UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
@Controller
@RequestMapping("/login")
@PreAuthorize("isAnonymous()")
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPasswordResetTokenService tokenService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CreateEmailMessage createEmailMessage;
    @Autowired
    private LocaleResolver localeResolver;


    @GetMapping
    public String index() {
        return "login/index";
    }

    @GetMapping(params = "reset_password")
    public String resetPassword() {
        return "login/reset_password";
    }

    @PostMapping(params = "reset_password")
    public String resetPassword(HttpServletRequest request,
                                @RequestParam("email") final String userEmail,
                                Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            model.addAttribute("message", messageSource.getMessage("message.userNotFound", null, request.getLocale()));
            return "redirect:/login.html?lang=";
        }
        String token = UUID.randomUUID().toString();
        tokenService.createPasswordResetToken(token, user);
        String appUrl = AppHelper.getAppUrl(request);
        try {
            createEmailMessage.sendResetPasswordEmail(appUrl, request.getLocale(), token, user);
        } catch (Exception e) {
            //todo
            return "redirect:/error";
        }
        model.addAttribute("message", messageSource.getMessage("message.login.reset_password", null, locale));
        return "login/info";
    }

    @GetMapping(params = {"token", "u"})
    public String showResetPassword(@RequestParam String token, @RequestParam Long u,
                                    HttpServletRequest request, Model model) {
        Locale locale = localeResolver.resolveLocale(request);
        PasswordResetToken resetToken = tokenService.findPasswordResetToken(token);
        User user;
        if(resetToken == null || !(user = resetToken.getUser()).getId().equals(u)){
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "login/info";
        }
        Calendar calendar = Calendar.getInstance();
        if(resetToken.getExpiryDate().getTime() - calendar.getTime().getTime()<=0){
            String message = messageSource.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            return "login/info";
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new UserDetailsImpl(user).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/login/change_password";
    }


}
