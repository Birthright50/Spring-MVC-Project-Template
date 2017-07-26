package com.birthright.web.controller;


import com.birthright.constants.Routes;
import com.birthright.constants.SecurityConstants;
import com.birthright.constants.SessionConstants;
import com.birthright.entity.PasswordResetToken;
import com.birthright.entity.User;
import com.birthright.entity.enumiration.Roles;
import com.birthright.service.interfaces.IPasswordResetTokenService;
import com.birthright.service.interfaces.ISecureService;
import com.birthright.service.interfaces.IUserService;
import com.birthright.util.EmailMessageSender;
import com.birthright.util.ApplicationHelper;
import com.birthright.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Birthright on 01.05.2016.
 */
//  UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
@Controller
public class LoginController {
    private static final String MESSAGE = "message";
    private final IUserService userService;
    private final IPasswordResetTokenService tokenService;
    private final MessageSource messageSource;
    private final EmailMessageSender emailMessageSender;
    private final LocaleResolver localeResolver;
    private final ISecureService secureService;

    @Autowired
    public LoginController(IUserService userService, IPasswordResetTokenService tokenService, MessageSource messageSource, EmailMessageSender emailMessageSender, LocaleResolver localeResolver, ISecureService secureService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.messageSource = messageSource;
        this.emailMessageSender = emailMessageSender;
        this.localeResolver = localeResolver;
        this.secureService = secureService;
    }

    /**
     * Show login page
     */
    @GetMapping(Routes.LOGIN_URI)
    public String index() {
        return Routes.LOGIN_VIEW;
    }

    /**
     * Show reset password page
     */
    @GetMapping(value = Routes.LOGIN_URI, params = "reset_password")
    public String resetPassword() {
        return Routes.LOGIN_RESET_PASSWORD_VIEW;
    }

    /**
     * @param userEmail  -  a user's email, which need to reset password
     * @param lastResend - The date when the user has requested a resending email If the user requests a letter in less
     *                   than 5 minutes ago - prohibit the sending and caution.
     * @return without Exception always redirect to Login Info Page, with Exception - to Error Page
     */

    @PostMapping(value = Routes.LOGIN_URI, params = "reset_password")
    public RedirectView resetPassword(HttpServletRequest request, @RequestParam("email") final String userEmail, RedirectAttributes redirectAttributes, HttpSession session, @SessionAttribute(required = false, name = SessionConstants.LAST_RESEND) Long lastResend) {
        if (lastResend != null && Calendar.getInstance().getTime().getTime() - lastResend <= 600000) {
            String message = messageSource.getMessage("auth.message.too_many_resend", null, request.getLocale());
            redirectAttributes.addFlashAttribute(MESSAGE, message);
            return new RedirectView(Routes.LOGIN_URI + "?reset_password");
        }
        Locale locale = localeResolver.resolveLocale(request);
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            String message = messageSource.getMessage("auth.message.not_found", null, request.getLocale());
            redirectAttributes.addFlashAttribute(MESSAGE, message);
            return new RedirectView(Routes.LOGIN_URI + "?reset_password");
        }
        String token = UUID.randomUUID().toString();
        tokenService.createPasswordResetToken(token, user);
        String appUrl = ApplicationHelper.getAppUrl(request);
        try {
            emailMessageSender.sendResetPasswordEmail(appUrl, request.getLocale(), token, user);
        } catch (Exception e) {
            redirectAttributes.addAttribute(MESSAGE, e.getMessage());
            return new RedirectView(Routes.ERROR_URI);
        }
        String message = messageSource.getMessage("login.message.reset_password", null, locale);
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        session.setAttribute(SessionConstants.LAST_RESEND, Calendar.getInstance().getTime().getTime());
        return new RedirectView(Routes.LOGIN_INFO_URI);
    }

    @GetMapping(value = Routes.LOGIN_URI, params = {"token", "u", "reset_password"})
    public RedirectView showResetPassword(@RequestParam String token, @RequestParam Long u, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Locale locale = localeResolver.resolveLocale(request);
        PasswordResetToken resetToken = tokenService.findPasswordResetToken(token);
        String invalidResult = secureService.checkConfirmResetPasswordToken(resetToken, u);
        if (invalidResult != null) {
            String message = messageSource.getMessage("auth.message." + invalidResult, null, locale);
            redirectAttributes.addFlashAttribute(MESSAGE, message);
            return new RedirectView(Routes.LOGIN_INFO_URI);
        }
        User user = resetToken.getUser();
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.singletonList(new SimpleGrantedAuthority(SecurityConstants.DEFAULT_ROLE_PREFIX + Roles.TEMPORARY_ACCESS.toString())));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView(Routes.LOGIN_NEW_PASSWORD_URI);
    }

    @GetMapping(Routes.LOGIN_NEW_PASSWORD_URI)
    public String showNewPasswordPage() {
        return Routes.LOGIN_NEW_PASSWORD_VIEW;
    }

    @PostMapping(Routes.LOGIN_NEW_PASSWORD_URI)
    public RedirectView saveNewPassword(@RequestParam String password, @RequestParam String matchingPassword, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (!password.equals(matchingPassword)) {
            String message = messageSource.getMessage("auth.message.wrong_match", null, localeResolver.resolveLocale(request));
            redirectAttributes.addFlashAttribute(MESSAGE, message);
            return new RedirectView(Routes.LOGIN_NEW_PASSWORD_URI);
        }
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = userDetails.getUser();
        userService.changeUserPassword(user, password);
        tokenService.deletePasswordResetToken(user);
        userDetails.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String message = messageSource.getMessage("auth.message.success_reset", null, localeResolver.resolveLocale(request));
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return new RedirectView(Routes.LOGIN_INFO_URI);
    }


    @GetMapping(Routes.LOGIN_INFO_URI)
    public String showLoginInfo(Model model) {
        if (model.containsAttribute(MESSAGE)) {
            return Routes.LOGIN_INFO_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

}
