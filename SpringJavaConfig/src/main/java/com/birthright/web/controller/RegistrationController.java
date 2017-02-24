package com.birthright.web.controller;


import com.birthright.constants.SessionConstants;
import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.event.OnRegistrationCompleteEvent;
import com.birthright.helper.CreateEmailMessage;
import com.birthright.service.IUserService;
import com.birthright.web.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Birthright on 30.04.2016.
 */
@Controller
@RequestMapping("/register")
@Log4j2
public class RegistrationController {

    private static final String modelName = "user";

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IUserService userService;
    @Autowired
    private MessageSource messages;
    @Autowired
    private CreateEmailMessage createEmailMessage;
    @Autowired
    private MailSender mailSender;

    @GetMapping
    public String index(ModelMap modelMap) {
        if (!modelMap.containsAttribute(modelName)) {
            modelMap.addAttribute(modelName, new UserDto());
        }
        return "register/index";
    }

    @PostMapping
    public RedirectView registerUser(@Valid @ModelAttribute(modelName) UserDto userDto,
                                     BindingResult result, RedirectAttributes redirectAttributes,
                                     HttpServletRequest request) {
        redirectAttributes.addFlashAttribute(modelName, userDto);
        if (result.hasErrors()) {
            return new RedirectView("/register");
        }
        User registered = userService.createUserAccount(userDto);
        if (registered == null) {
            result.rejectValue("email", "message.reg_email_error");
            result.rejectValue("username", "message.reg_username_error");
            return new RedirectView("/register");
        }
        final String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                                                (registered, request.getLocale(), appUrl));
        } catch (Exception e) {
            e.printStackTrace();
            //todo
        }
        return new RedirectView("/register?success");
    }


    @GetMapping(params = {"token", "u"})
    public String confirmRegistrationByToken(WebRequest request,
                                             @RequestParam String token,
                                             @RequestParam Long u,
                                             Model model,
                                             HttpSession session,
                                             @CookieValue(value = "i18n", required = false) String i18n) {

        Locale locale = (i18n != null) ? LocaleUtils.toLocale(i18n) : request.getLocale();
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "register/bad_token";
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            return "register/bad_token";
        }
        User user = verificationToken.getUser();
        if (!Objects.equals(user.getId(), u)) {
            String message = messages.getMessage("auth.message.not_match", null, locale);
            model.addAttribute("message", message);
            return "register/bad_token";
        }
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        userService.deleteVerificationToken(verificationToken);
        session.removeAttribute(SessionConstants.LAST_RESEND);
        session.removeAttribute(SessionConstants.EXISTING_TOKEN);
        return "redirect:/login";
    }

    @GetMapping(params = "resend_token")
    public String resendRegistrationToken(final @SessionAttribute(required = false) String existingToken,
                                          HttpServletRequest request, HttpSession session,
                                          @SessionAttribute(required = false) Long lastResend,
                                          Model model,
                                          @CookieValue(value = "i18n", required = false) String i18n) {
        if (existingToken != null) {
            Calendar cal = Calendar.getInstance();
            if (lastResend != null && cal.getTime().getTime() - lastResend <= 300000) {
                model.addAttribute("tooManyResend", true);
                return "register/resend";
            }
            Locale locale = (i18n != null) ? LocaleUtils.toLocale(i18n) : request.getLocale();
            final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
            final String appUrl = request.getScheme() + "://" + request.getServerName() +
                    ":" + request.getServerPort() + request.getContextPath();
            SimpleMailMessage mailMessage =
                    createEmailMessage.constructResetVerificationTokenEmail(appUrl, locale, newToken, newToken.getUser(), messages);
            try {
                mailSender.send(mailMessage);
                session.setAttribute(SessionConstants.EXISTING_TOKEN, newToken.getToken());
            } catch (MailException e) {
                log.debug("Mail Exception", e);
                return "redirect:/error";
            } catch (Exception e) {
                log.debug(e.getLocalizedMessage(), e);
                return "redirect:/error";
            }
            session.setAttribute(SessionConstants.LAST_RESEND, Calendar.getInstance().getTime().getTime());
            return "register/resend";
        }
        return "redirect:/";
    }


    @GetMapping(params = "success")
    public String successRegistration(ModelMap modelMap) {
        if (modelMap.containsAttribute(modelName)) {
            return "register/success";
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
