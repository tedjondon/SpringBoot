package com.zabello.springboot;

import com.zabello.springboot.model.Role;
import com.zabello.springboot.model.User;
import com.zabello.springboot.service.RoleService;
import com.zabello.springboot.service.UserService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class CreateUsers {

    private UserService userService;
    private RoleService roleService;

    public CreateUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @PostConstruct
    public void createUsers() {

        Set<Role> roles = new HashSet<>();
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        roles.add(userRole);
        roles.add(adminRole);
        roleService.add(userRole);
        roleService.add(adminRole);
        User admin = new User("admin", "test", roles);
        userService.add(admin);

        Set<Role> roles2 = new HashSet<>();
        roles2.add(userRole);
        User user = new User("user", "test", roles2);
        userService.add(user);
    }
}
