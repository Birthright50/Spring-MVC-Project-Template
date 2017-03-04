package com.birthright.infrastructure.configuration.security;

import com.birthright.constants.Routes;
import com.birthright.enumiration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Created by birth on 26.01.2017.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${cookie.remember_me_age}")
    private int rememberMeAge;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // <http pattern="/resources/**" security="none"/>
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers("/personal_cabinet/**").hasRole(Role.USER.name())
                .antMatchers(Routes.LOGIN_URI + "/new_password/**").hasRole(Role.TEMPORARY_ACCESS.name())
                .antMatchers(Routes.LOGIN_URI + "/**").anonymous()

                .antMatchers(Routes.REGISTRATION_URI + "/**").anonymous()
                .and()
                .formLogin().loginPage(Routes.LOGIN_URI).usernameParameter("name")
                .passwordParameter("password").failureUrl(Routes.LOGIN_URI + "?error").loginProcessingUrl("/login_processing")
                .defaultSuccessUrl("/", false)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .rememberMe().useSecureCookie(true).rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me").userDetailsService(userDetailsService)
                .tokenValiditySeconds(rememberMeAge)
                .and()
                .csrf()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and().sessionManagement().maximumSessions(2).expiredUrl("/session_expired");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedServletRequestHandler();
    }

}
