package com.birthright.web.controller;


import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.event.OnRegistrationCompleteEvent;
import com.birthright.service.IUserService;
import com.birthright.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Birthright on 30.04.2016.
 */
@Controller
@Slf4j
@RequestMapping("/registration")
public class RegistrationController {

    private static final String modelName = "user";

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IUserService userService;
    @Autowired
    private MessageSource messages;

    @GetMapping()
    public String index(ModelMap modelMap) {
        if (!modelMap.containsAttribute(modelName)) {
            modelMap.addAttribute(modelName, new UserDto());
        }
        return "registration/index";
    }

    @PostMapping()
    public RedirectView fresh_user(@Valid @ModelAttribute("user") UserDto userDto,
                                   BindingResult result, RedirectAttributes redirectAttributes,
                                   WebRequest request) {
        redirectAttributes.addFlashAttribute(modelName, userDto);
        if (result.hasErrors()) {
            return new RedirectView("/registration");
        }
        User registered = userService.createUserAccount(userDto);
        if (registered == null) {
            result.rejectValue("email", "message.reg_email_error");
            result.rejectValue("username", "message.reg_username_error");
            return new RedirectView("/registration");
        }
        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (registered, request.getLocale(), appUrl));
        } catch (Exception exception) {
            return new RedirectView("/error");
        }
        return new RedirectView("/registration/success");
    }


    @GetMapping("confirm")
    public RedirectView confirmRegistration(WebRequest request,
                                            @RequestParam("token") String token,
                                            RedirectAttributes redirectAttributes) {
        Locale locale = request.getLocale();
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            redirectAttributes.addFlashAttribute("message", message);
            return new RedirectView("/registration/bad_token?locale=" + locale.getLanguage());
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            redirectAttributes.addFlashAttribute("message", messageValue);
            return new RedirectView("/registration/bad_token?locale=" + locale.getLanguage());
        }

        user.setEnabled(true);
       // userService.saveRegisteredUser(user);
        return new RedirectView("/registration/bad_token?locale=" + locale.getLanguage());
    }

    @GetMapping("success")
    public String success(ModelMap modelMap) {
        if (modelMap.containsAttribute(modelName)) {
            return "registration/success";
        }
        return "redirect:/";
    }

    @PostMapping("check_user")
    @ResponseBody
    public String checkUserIfExists(@RequestParam String username,
                                    @RequestParam String email) {
        return userService.checkUserIsExists(username, email);
    }


}
