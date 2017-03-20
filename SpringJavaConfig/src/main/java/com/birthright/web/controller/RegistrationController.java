package com.birthright.web.controller;


import com.birthright.constants.Routes;
import com.birthright.constants.SessionConstants;
import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.event.OnRegistrationCompleteEvent;
import com.birthright.service.interfaces.ISecureService;
import com.birthright.service.interfaces.IUserService;
import com.birthright.service.interfaces.IVerificationTokenService;
import com.birthright.util.CreateEmailMessageHelper;
import com.birthright.util.UrlApplicationHelper;
import com.birthright.validation.TokenNotFoundException;
import com.birthright.web.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Birthright on 30.04.2016.
 */
@Controller
@Log4j2
public class RegistrationController {
    private static final String MESSAGE = "message";
    private static final String modelName = "user";
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVerificationTokenService tokenService;
    @Autowired
    private ISecureService secureService;
    @Autowired
    private MessageSource messages;
    @Autowired
    private CreateEmailMessageHelper createEmailMessageHelper;
    @Autowired
    private LocaleResolver localeResolver;
    /**
     * Registration process
     */
    @GetMapping(Routes.REGISTRATION_URI)
    public String index(ModelMap modelMap) {
        if (!modelMap.containsAttribute(modelName)) {
            modelMap.addAttribute(modelName, new UserDto());
        }
        return Routes.REGISTRATION_VIEW;
    }

    /**
     * @param userDto - User Data Transfer Object, need to validate our registration form
     */
    @PostMapping(Routes.REGISTRATION_URI)
    public RedirectView registerUser(@Valid @ModelAttribute(modelName) UserDto userDto,
                                     BindingResult result, RedirectAttributes redirectAttributes,
                                     HttpServletRequest request) {
        redirectAttributes.addFlashAttribute(modelName, userDto);
        if (result.hasErrors()) {
            return new RedirectView(Routes.REGISTRATION_URI);
        }
        User registered = userService.createUserAccount(userDto);
        if (registered == null) {
            result.rejectValue("email", "register.message.email_error");
            result.rejectValue("username", "register.message.username_error");
            return new RedirectView(Routes.REGISTRATION_URI);
        }
        String appUrl = UrlApplicationHelper.getAppUrl(request);
        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                                                                        localeResolver.resolveLocale(request), appUrl));
        } catch (Exception e) {
            redirectAttributes.addAttribute(MESSAGE, e.getMessage());
            return new RedirectView(Routes.ERROR_URI);
        }
        return new RedirectView(Routes.REGISTRATION_SUCCESS_URI);
    }


    /**
     * Show success page after POST registration.
     *
     * @return If some wise guy will go here after not registering - redirect to the root, else show page.
     */
    @GetMapping(value = Routes.REGISTRATION_URI, params = "success")
    public String successRegistration(ModelMap modelMap) {
        if (modelMap.containsAttribute(modelName)) {
            return Routes.REGISTRATION_SUCCESS_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

    /**
     * Confirm registration token from email message,
     *
     * @param token - VerificationToken
     * @param u     - User ID.
     */
    @GetMapping(value = Routes.REGISTRATION_URI, params = {"token", "u"})
    public String confirmRegistrationByToken(HttpServletRequest request,
                                             @RequestParam String token,
                                             @RequestParam Long u,
                                             Model model,
                                             HttpSession session,
                                             RedirectAttributes redirectAttributes) {
        Locale locale = localeResolver.resolveLocale(request);
        VerificationToken verificationToken = tokenService.findVerificationToken(token);
        String invalidResult = secureService.checkConfirmRegistrationToken(verificationToken, u);
        if (invalidResult != null) {
            String message = messages.getMessage("register.message." + invalidResult, null, locale);
            model.addAttribute(MESSAGE, message);
            return Routes.REGISTRATION_INFO_VIEW;
        }
        session.removeAttribute(SessionConstants.LAST_RESEND);
        session.removeAttribute(SessionConstants.EXISTING_TOKEN);
        String message = messages.getMessage("register.message.success_confirmation", null, locale);
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return "redirect:" + Routes.LOGIN_URI;
    }

    /**
     * Resend verification email message, if user hasn't received a letter
     *
     * @param existingToken -  The current token after registration is stored in session, if it's not - permit request
     *                      and redirect to root.
     * @param lastResend    - The date when the user has requested a resending email If the user requests a letter in
     *                      less than 5 minutes ago - prohibit the sending and caution.
     */
    @GetMapping(value = Routes.REGISTRATION_URI, params = "resend_token")
    public String resendRegistrationToken(final @SessionAttribute(value = SessionConstants.EXISTING_TOKEN, required = false) String existingToken,
                                          HttpServletRequest request, HttpSession session,
                                          @SessionAttribute(required = false) Long lastResend,
                                          Model model) {
        if (existingToken != null) {
            Calendar cal = Calendar.getInstance();
            if (lastResend != null && cal.getTime().getTime() - lastResend <= 300000) {
                model.addAttribute("tooManyResend", true);
                return Routes.REGISTRATION_RESEND_TOKEN_VIEW;
            }
            Locale locale = localeResolver.resolveLocale(request);
            final VerificationToken newToken;
            try {
                newToken = tokenService.createNewVerificationToken(existingToken);
            } catch (TokenNotFoundException e) {
                //todo
                return "redirect:" + Routes.ERROR_URI;
            }

            String appUrl = UrlApplicationHelper.getAppUrl(request);
            try {
                createEmailMessageHelper.resendVerificationTokenEmail(appUrl, locale, newToken, newToken.getUser());
                session.setAttribute(SessionConstants.EXISTING_TOKEN, newToken.getToken());
            } catch (MailException e) {
                log.debug("Mail Exception", e);
                return "redirect:" + Routes.ERROR_URI;
            } catch (Exception e) {
                log.debug(e.getLocalizedMessage(), e);
                return "redirect:" + Routes.ERROR_URI;
            }
            session.setAttribute(SessionConstants.LAST_RESEND, Calendar.getInstance().getTime().getTime());
            return Routes.REGISTRATION_RESEND_TOKEN_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

}
