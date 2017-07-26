package com.birthright.listeners;

import com.birthright.entity.Role;
import com.birthright.entity.User;
import com.birthright.entity.enumiration.Roles;
import com.birthright.repository.RoleRepository;
import com.birthright.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by birthright on 04.03.17.
 */
@Component
public class SetupDataOnStartListener implements ApplicationListener<ContextRefreshedEvent> {
    private volatile boolean isAlreadySetup;
    private final IUserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public SetupDataOnStartListener(IUserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!this.isAlreadySetup) {
            Role admin = this.roleRepository.save(new Role(Roles.ADMIN));
            this.roleRepository.save(new Role(Roles.USER));
            User user = User.builder()
                    .email("birthright5050@gmail.com")
                    .username("Birthright")
                    .enabled(true)
                    .roles(Collections.singletonList(admin))
                    .password("Qwerty123").build();
            this.userService.save(user, true);
            this.isAlreadySetup = true;
        }
    }
}
