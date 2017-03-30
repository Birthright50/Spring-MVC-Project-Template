package com.birthright.listeners;

import com.birthright.entity.Role;
import com.birthright.entity.User;
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
    private boolean isAlreadySetup = false;
    @Autowired
    private IUserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isAlreadySetup) {
            return;
        }
        Role admin = roleRepository.save(new Role(com.birthright.enumiration.Role.ADMIN.name()));
        roleRepository.save(new Role(com.birthright.enumiration.Role.USER.name()));
        User user = User.builder().email("birthright5050@gmail.com").username("Birthright").enabled(true).roles(Collections.singletonList(admin)).password("Qwerty123").build();
        userService.save(user, true);
        isAlreadySetup = true;
    }
}
