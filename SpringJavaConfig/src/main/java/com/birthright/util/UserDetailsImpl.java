package com.birthright.util;


import com.birthright.entity.Role;
import com.birthright.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    @Getter
    @Setter
    private User user;

    /**
     * @return коллекция прав доступа пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities(user.getRoles());
    }

    private static Collection<GrantedAuthority> getAuthorities (Collection<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /**
     * hash of password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Логин (имя) пользователя
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * @return флаг, что срок действия аккаунта еще не истек, он активен
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return флаг, что пользователь не заблокирован администраторами сайта
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return флаг, что срок действия пароля еще не истек, он активен
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return флаг, что пользователь включен и подтвержден
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }




}
